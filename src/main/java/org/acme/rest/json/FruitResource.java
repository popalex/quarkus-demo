package org.acme.rest.json;

import java.lang.System.Logger.Level;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/fruits")
public class FruitResource {

    private Set<Fruit> fruits = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
    static System.Logger LOG = System.getLogger(FruitResource.class.getName());

    public FruitResource() {
        fruits.add(new Fruit("Apple", "Winter fruit"));
        fruits.add(new Fruit("Pineapple", "Tropical fruit"));
        LOG.log(Level.DEBUG, "FruitResource INIT - adding initial fruits ");
    }

    @GET
    public Set<Fruit> list() {
        LOG.log(Level.DEBUG, "GET fruits");
        return fruits;
    }

    @POST
    public Set<Fruit> add(Fruit fruit) {
        LOG.log(Level.DEBUG, "POST fruits");
        fruits.add(fruit);
        return fruits;
    }

    @DELETE
    public Set<Fruit> delete(Fruit fruit) {
        LOG.log(Level.DEBUG, "DELETE fruits");
        fruits.removeIf(existingFruit -> existingFruit.name.contentEquals(fruit.name));
        return fruits;
    }
}
