/*
 * Copyright 2014-2016 Message4U Pty Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.messagemedia.restapi.client.v1;

import com.github.kristofa.test.http.*;
import com.messagemedia.restapi.client.v1.messaging.RestApiMessagingClient;
import com.messagemedia.restapi.client.v1.messaging.messages.Message;
import com.messagemedia.restapi.client.v1.messaging.messages.MessageBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.messagemedia.restapi.client.v1.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpConnectionTest {

    private static final int MOCK_HTTP_PORT = 9636;
    private static final int MAX_CONNECTIONS_FOR_TEST = 1;
    private static final int TIMEOUT = (int) TimeUnit.SECONDS.toMillis(1);

    private RestApiMessagingClient messagingClient;
    private MockHttpServer server;
    private SendMessagesResponseProvider responseProvider;

    private static final class SendMessagesResponseProvider implements HttpResponseProvider {

        private long waitTime = 0L;

        private String response = "{\"messages\" : [{}]}";

        public void setWaitTime(long waitTime) {
            this.waitTime = waitTime;
        }

        @Override
        public HttpResponse getResponse(HttpRequest request) {
            return new HttpResponse() {

                @Override
                public int getHttpCode() {
                    return 202;
                }

                @Override
                public String getContentType() {
                    return MediaType.APPLICATION_JSON_UTF8.getValue();
                }

                @Override
                public byte[] getContent() {
                    try {
                        Thread.sleep(waitTime);
                    } catch (InterruptedException e) {
                    }
                    try {
                        return response.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
        }

        public void setResponse(String response) {
            this.response = response;
        }

        @Override
        public void verify() throws UnsatisfiedExpectationException {
        }

    }

    @Before
    public void setUp() throws IOException {
        responseProvider = new SendMessagesResponseProvider();
        this.server = new MockHttpServer(MOCK_HTTP_PORT, responseProvider);
        this.server.start();
        RestApiClient restClient = RestApiClientBuilder.newBuilder(API_KEY, SECRET_KEY)
                                                       .endpoint("http://localhost:" + MOCK_HTTP_PORT + "/")
                                                       .maxConnections(MAX_CONNECTIONS_FOR_TEST).socketTimeout(TIMEOUT).build();
        this.messagingClient = restClient.messaging();
    }

    @After
    public void tearDown() throws IOException {
        this.server.stop();
    }

    @Test
    public void testIfConnectionsAreBeingReused() throws RestApiException {

        responseProvider.setResponse("{\"messages\": [ { " +
                                             "\"content\": \"Hello, World!\", " +
                                             "\"destination_number\": \"" + TEST_NUMBER_1 + "\", " +
                                             "\"message_id\": \"test\", " +
                                             "\"status\": \"queued\"" +
                                             " } ] }");

        final MessageBuilder messageBuilder = new MessageBuilder().content("Hello, World!").destinationNumber(TEST_NUMBER_1);

        for (int i = 0; i < MAX_CONNECTIONS_FOR_TEST + 10; i++) {
            this.messagingClient.sendMessage(messageBuilder.build());
        }
    }

    @Test
    public void testMultiThreadsCallsWithSameClient() throws InterruptedException {

        responseProvider.setResponse("{\"messages\": [ { " +
                                             "\"content\": \"Hello, World!\", " +
                                             "\"destination_number\": \"" + TEST_NUMBER_1 + "\", " +
                                             "\"message_id\": \"test\", " +
                                             "\"status\": \"queued\"" +
                                             " } ] }");

        final MessageBuilder messageBuilder = new MessageBuilder().content("Hello, World!").destinationNumber(TEST_NUMBER_1);

        final AtomicInteger failureCounter = new AtomicInteger(0);
        final CountDownLatch startSignal = new CountDownLatch(1);

        int numberOfThreads = 50;

        ExecutorService threadPool = Executors.newFixedThreadPool(numberOfThreads);

        final AtomicInteger counter = new AtomicInteger();
        for (int i = 0; i < numberOfThreads; i++) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        startSignal.await();
                        messagingClient.sendMessage(messageBuilder.build());
                        counter.incrementAndGet();
                    } catch (Exception e) {
                        failureCounter.incrementAndGet();
                    }
                }
            });
        }

        // Allow threads to begin processing requests
        startSignal.countDown();

        // Initiates an orderly shutdown
        threadPool.shutdown();

        // Wait for up to 3 minutes for threads to terminate
        assertTrue(threadPool.awaitTermination(3, TimeUnit.MINUTES));

        // Ensure that all threads completed successfully
        assertEquals(numberOfThreads, counter.get());
        assertEquals(0, failureCounter.get());
    }

    @Test(expected = RestApiException.class)
    public void testTimeout() throws RestApiException {
        final MessageBuilder messageBuilder = new MessageBuilder().content("Hello, World!").destinationNumber(TEST_NUMBER_1);
        this.responseProvider.setWaitTime(TIMEOUT + 100);
        this.messagingClient.sendMessage(messageBuilder.build());
    }

}
