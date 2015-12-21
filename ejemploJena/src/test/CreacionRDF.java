/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.VCARD;

public class CreacionRDF {
	
	/**
	 * muestra un modelo de jena de ejemplo por pantalla
	 */
	public static void main (String args[]) {
        Model model = CreacionRDF.generarEjemplo();
        // write the model in the standar output
        model.write(System.out); 
    }
	
	/**
	 * Genera un modelo de jena de ejemplo
	 */
	public static Model generarEjemplo(){
		// definiciones
        String personURI    = "http://somewhere/JohnSmith";
        String givenName    = "John";
        String familyName   = "Smith";
        String fullName     = givenName + " " + familyName;

        // crea un modelo vacio
        Model model = ModelFactory.createDefaultModel();
        Property type = model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        Resource person = model.createResource("http://xmlns.com/foaf/0.1/person");
        Property knows = model.createProperty("http://xmlns.com/foaf/0.1/knows");

        // le añade las propiedades
        Resource johnSmith  = model.createResource(personURI)
        		.addProperty(VCARD.FN, fullName)
        		.addProperty(VCARD.N, 
                      model.createResource()
                           .addProperty(VCARD.Given, givenName)
                           .addProperty(VCARD.Family, familyName));
    	johnSmith.addProperty(type, person);
        
    	Resource ladyPeanut  = model.createResource("http://somewhere/LadyPeanut")
    			.addProperty(VCARD.FN, "Lady Peanut")
	            .addProperty(VCARD.N, 
	                     model.createResource()
	                          .addProperty(VCARD.Given, "Lady")
	                          .addProperty(VCARD.Family, "Peanut"));
       	ladyPeanut.addProperty(type, person);
        
       	Resource donRondon  = model.createResource("http://somewhere/DonRondon")
       			.addProperty(VCARD.FN, "Don Rondon")
       			.addProperty(VCARD.N, 
                        model.createResource()
                             .addProperty(VCARD.Given, "Don")
                             .addProperty(VCARD.Family, "Rondon"));
       	donRondon.addProperty(type, person);
        
        ladyPeanut.addProperty(knows, donRondon);
       	
       	return model;
	}
	
	
}
