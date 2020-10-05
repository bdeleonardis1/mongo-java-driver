/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package reactivestreams.tour;

//import com.mongodb.reactivestreams.client.MongoClient;
//import com.mongodb.reactivestreams.client.MongoClients;
//import com.mongodb.reactivestreams.client.MongoCollection;
//import com.mongodb.reactivestreams.client.MongoDatabase;
//import org.bson.Document;
//import reactivestreams.helpers.SubscriberHelpers;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.HashMap;
import java.util.Map;

/**
 * The QuickTour code example
 */
public class QuickTour {
    /**
     * Run this main method to see the output of this quick example.
     *
     * @param args takes an optional single argument for the connection string
     */
    public static void main(final String[] args) {
        try (MongoClient client = MongoClients.create()) {
            ReactiveMongoTemplate template = new ReactiveMongoTemplate(client, "test");

            String tel = "111111";
            System.out.println("Before save");
            Map<String, Object> data = new HashMap<>(Map.of("tel", tel, "child", Map.of("name", "Lily")));
            template.save(data, template.getCollectionName(Family.class)).block();

            System.out.println("After save");

            template.findOne(Query.query(Criteria.where("tel").is(tel)), Family.class)
                    .doOnError(QuickTour::onError)
                    .block();

            System.out.println("After findOne");
        }

//        MongoClient mongoClient = MongoClients.create();
//        MongoDatabase database = mongoClient.getDatabase("reactive");
//        final MongoCollection<Document> collection = database.getCollection("test");
//
//        SubscriberHelpers.SingleSubscriber<Document> documentSubscriber = new SubscriberHelpers.SingleSubscriber<>();
//        collection.find().first().subscribe(documentSubscriber);
//        documentSubscriber.await();
    }

    public static void onError(Throwable throwable) {
        System.out.println("Are we going in here or what");
    }

    public static abstract class Child {
        private String name = "Unknown";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static final class Son extends Child {

    }

    public static final class Family {

        private String tel = "000000";

        private Child child = new Son();

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public Child getChild() {
            return child;
        }

        public void setChild(Child child) {
            this.child = child;
        }
    }
}
