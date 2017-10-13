/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>, Blueprint> blueprints=new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        /**
        inicializar datos ("stub data") en la memoria
        */

        Point[] pts1 = new Point[] { new Point(40, 50), new Point(105, 15) };
        Point[] pts2 = new Point[] { new Point(10, 10), new Point(51, 51) };
        Point[] pts3 = new Point[] { new Point(90, 90), new Point(30, 30) };

        Point[] pts4 = new Point[] { new Point(50, 200), new Point(200, 51), new Point(400, 200),
                new Point(51, 200), new Point(50, 300), new Point(400, 300), new Point(400, 201) };

        Point[] pts5 = new Point[] { new Point(185, 30), new Point(190, 10), new Point(195, 30),
                new Point(180, 20), new Point(200, 20), new Point(180, 31) };

// Hexágono
        Point[] pts6 = new Point[] { new Point(150, 100), new Point(200, 100), new Point(225, 150),
                new Point(200, 200), new Point(150, 200), new Point(125, 150) };

        Blueprint bp1 = new Blueprint("Saray", "plano1", pts1);
        Blueprint bp2 = new Blueprint("Saray", "plano2", pts2);
        Blueprint bp3 = new Blueprint("Alieth", "plano3", pts3);
        Blueprint bp4 = new Blueprint("Alieth", "casa", pts4);
        Blueprint bp5 = new Blueprint("Alieth", "estrella", pts5);
        Blueprint bp6 = new Blueprint("Alieth", "hexagono", pts6);

        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        blueprints.put(new Tuple<>(bp4.getAuthor(),bp4.getName()), bp4);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp5.getAuthor(),bp5.getName()), bp5);
        blueprints.put(new Tuple<>(bp6.getAuthor(),bp6.getName()), bp6);
    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> bp = new HashSet<>();
        Set<Tuple<String, String>> keys = blueprints.keySet();

        for(Tuple<String, String> tuple : keys){
            if(tuple.getElem1().equals(author)){
                bp.add(blueprints.get(tuple));
            }
        }
        if(bp.isEmpty()){
            throw new BlueprintNotFoundException(BlueprintNotFoundException.NO_AUTOR);
        }
        return bp;
    }

    @Override
    public Set<Blueprint> getAllBlueprint() throws BlueprintNotFoundException {
        Set<Blueprint> bp = new HashSet<>();
        Set<Tuple<String, String>> keys = blueprints.keySet();

        for(Tuple<String, String> tuple : keys){
            bp.add(blueprints.get(tuple));
        }

        return bp;
    }


}
