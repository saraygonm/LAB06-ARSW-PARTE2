/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;

/**
 *
 * @author hcadavid
 */

@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {
    @Autowired
    BlueprintsServices blueprintsServices;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> GetResource() {
        //obtener datos que se enviarán a través del API
        //data
        try {
            return new ResponseEntity<>(blueprintsServices.getAllBlueprints(), HttpStatus.ACCEPTED);
        } catch (BlueprintPersistenceException ex){
            return new ResponseEntity<>("Blue print error",HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{author}")
    @ResponseBody
    public ResponseEntity<?> getBlueprintByAuthor(@PathVariable("author") String author) {
        try {
            return new ResponseEntity<>(blueprintsServices.getBlueprintByAuthor(author),HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            return new ResponseEntity<>("404 ERROR \n The blueprint by Author +"+author+" wasn't found",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{author}/{bpname}")
    public ResponseEntity<?> getBlueprint(@PathVariable("author") String author, @PathVariable("bpname") String bpname){
        try {
            return new ResponseEntity<>(blueprintsServices.getBlueprint(author,bpname),HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex){
            return new ResponseEntity<>("404 ERROR \n The blueprint wasn't found ",HttpStatus.NOT_FOUND);
        }
    }

    //@GetMapping("/blueprints/{author}/{bpname}")
    @RequestMapping(method = RequestMethod.PUT, value="{author}/{bpname}/{points}")
    public ResponseEntity<?> updateNewBlueprint(@PathVariable String author, @PathVariable String bpname,@PathVariable String points){
        Point[] pts1=convertStringtoObject(points);
        blueprintsServices.updateBlueprint(author,bpname,pts1);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //doc/BlueprintPostTry/"{'x':140,'y':140},{'x':115,'y':115}"
    @RequestMapping(method = RequestMethod.POST, value="{author}/{bpname}/{points}")
    public ResponseEntity<?> addNewBlueprint(@PathVariable String author, @PathVariable String bpname, @PathVariable String points){
        try {
            Point[] pts1=convertStringtoObject(points);
            Blueprint bp=new Blueprint(author, bpname,pts1);
            blueprintsServices.addNewBlueprint(bp);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (BlueprintPersistenceException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "{author}/{bpname}")
    public ResponseEntity<?> deleteBlueprint(@PathVariable String author, @PathVariable String bpname){
        try {
            blueprintsServices.deleteBlueprint(author,bpname);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method add the points string to an array
     * @param points
     * @return
     */
    private Point[] convertStringtoObject(String points){
        String[] strArray = points.split(":");
        Point[] pts1=new Point[strArray.length/2];
        int index  = 0;
        for (int i = 1; i< strArray.length; i+=2){
            Point point = null;
            try{
                int x = Integer.parseInt(strArray[i].replace("}","").replace(",","").replace("'y'","").replace("{","").replace("'x'",""));
                int y = 0 ;
                if (i==strArray.length-2) {
                    String s = strArray[i + 1].replace("}", "").replace(",", "").replace("'y'", "").replace("{", "").replace("'x'", "");
                    s = s.substring(0, s.length() - 1);
                    y = Integer.parseInt(s);
                }else{y = Integer.parseInt(strArray[i + 1].replace("}", "").replace(",", "").replace("'y'", "").replace("{", "").replace("'x'", ""));}
                point = new Point(x,y);
            }
            catch (NumberFormatException ex){
                point = new Point(0,0);
            }pts1[index] = point;
            index++;
        }
        return pts1;
    }

}

