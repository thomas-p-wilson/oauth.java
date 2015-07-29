/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thomaspwilson.oauth2.provider.impl.memory.service.memory;

import com.thomaspwilson.oauth2.provider.exception.InvalidClientException;
import com.thomaspwilson.oauth2.provider.model.Client;
import com.thomaspwilson.oauth2.provider.service.ClientService;
import java.util.HashMap;
import java.util.Map;

public class InMemoryClientServiceImpl implements ClientService {
    private final Map<String, Client> clients = new HashMap<>();
    
    //~ Construction ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public InMemoryClientServiceImpl() {}
    public InMemoryClientServiceImpl(Client...clients) {
        for (Client client : clients) {
            this.clients.put(client.getId(), client);
        }
    }
    
    //~ ClientService impl ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public Client find(final String id) throws InvalidClientException {
        if (!clients.containsKey(id)) {
            throw new InvalidClientException("Invalid client credentials");
        }
        return clients.get(id);
    }

    @Override
    public Client findAuthenticated(final String id, final String secret)
            throws InvalidClientException {
        final Client client = find(id);
        if (!client.getSecret().equals(secret)) {
            throw new InvalidClientException("Invalid client credentials");
        }
        return client;
    }

    @Override
    public void save(final Client client) {
        if (clients.containsKey(client.getId())) {
            throw new InvalidClientException("Client already exists");
        }
        clients.put(client.getId(), client);
    }

    @Override
    public void delete(final Client client) {
        if (clients.containsKey(client.getId())) {
            clients.remove(client.getId());
        }
    }
    
    
}