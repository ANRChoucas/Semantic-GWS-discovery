package Functional_matching_calculation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//A class containing a method for functionality semantic similarity calculation

public class Functionality_fileAnalyzer_Calculation {

	// A method to parse the description file and retrieve the semantic annotations
	// related to the service functionality and calculate the semantic
	// similarity for the searched functionality.
	public static List<List<String>> CalculSimFunctionnality(String ClassRequete)
			throws ParserConfigurationException, SAXException, IOException {

		List<List<String>> ListResultSimFunc = new ArrayList<List<String>>();

		File repertoire = new File("./data/Functional_Description/");
		String liste[] = repertoire.list();
		String baseOntoService = "http://www.semanticweb.org/home/ontologies/2018/6/untitled-ontology-6#";

		if (liste != null) {
			for (int j = 0; j < liste.length; j++) {

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder loader = factory.newDocumentBuilder();
				Document xmlDoc = loader.parse("./data/Functional_Description/" + liste[j]);
				NodeList ListProcess = (xmlDoc).getElementsByTagName("wps:Process");

				for (int i = 0; i < ListProcess.getLength(); i++) {

					Element ElementProcess = (Element) ListProcess.item(i);
					NodeList ListeMetadata = ElementProcess.getElementsByTagName("ows:Metadata");
					Element ElementMetadata = (Element) ListeMetadata.item(0);
					String role = ElementMetadata.getAttribute("xlink:role");
					if (role.equals("http://www.opengis.net/spec/wps/2.0/def/process-profile/concept_functionality")) {
						List<String> ResultatSimlilarité = new ArrayList<String>();
						String hrefFunctionnalité = ElementMetadata.getAttribute("xlink:href");
						// *****similarity calculation*******

						float sim = Concepts_Similarity_Calculation.SimCalc("./data/ServiceOntology.owl",
								baseOntoService + ClassRequete, hrefFunctionnalité);

						ResultatSimlilarité.add(hrefFunctionnalité);
						ResultatSimlilarité.add(Float.toString(sim));
						ResultatSimlilarité.add(liste[j]);
						ListResultSimFunc.add(ResultatSimlilarité);
					}
				}

			}
		} else {
			System.err.println("Invalid directory name");
		}

		return ListResultSimFunc;
	}

}
