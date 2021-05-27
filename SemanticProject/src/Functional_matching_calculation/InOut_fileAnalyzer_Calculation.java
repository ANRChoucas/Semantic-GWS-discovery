package Functional_matching_calculation;

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

// A class containing a method for inputs and outputs semantic similarity calculation
public class InOut_fileAnalyzer_Calculation {

	// A method to parse the functional description file and retrieve the semantic
	// annotations related to inputs and outputs of the service and calculate the
	// semantic similarity for the searched inputs and outputs.
	public static List<String> découverteRequete(String ServiceIdentifier, List<String> ConceptsRequeteIN,
			List<String> ConceptsRequeteOut) throws ParserConfigurationException, SAXException, IOException {

		String ServicePath = null;
		List<String> ConceptsInService = new ArrayList<String>();
		List<String> ConceptsOutService = new ArrayList<String>();
		List<String> ListSimMaxInService = new ArrayList<String>();
		List<String> ListSimMaxOutRequest = new ArrayList<String>();
		List<String> simList = new ArrayList<String>();
		String baseOntoData = "http://www.semanticweb.org/halilali/ontologies/2020/0/untitled-ontology-25#";
		float SimPartialIn = 0;
		float SimPartialOut = 0;
		float maxSimC1C2In = 0;
		float maxSimC1C2Out = 0;
		float SimIn = 0;
		float SimOut = 0;
		float SommeSimIn = 0;
		float SommeSimOut = 0;
		float simGlo = 0;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder loader = factory.newDocumentBuilder();
		ServicePath = "./data/Functional_Description/" + ServiceIdentifier;
		Document xmlDocS1 = loader.parse(ServicePath);

		// ***************Get list of inputs Service****************

		NodeList ListInput = (xmlDocS1).getElementsByTagName("wps:Input");

		for (int i = 0; i < ListInput.getLength(); i++) {

			Element ElementInput = (Element) ListInput.item(i);
			NodeList listeMetadataInput = ElementInput.getElementsByTagName("ows:Metadata");
			for (int j = 0; j < listeMetadataInput.getLength(); j++) {

				Element ElementMetadataInput = (Element) listeMetadataInput.item(j);
				String roleInput = ElementMetadataInput.getAttribute("xlink:role");
				String refInput = ElementMetadataInput.getAttribute("xlink:href");

				if (roleInput.equals("http://www.opengis.net/spec/wps/2.0/def/concept_Input")) {
					ConceptsInService.add(refInput);
				}
			}

		}

		// ************Get list of outputs service***********************

		NodeList ListOutput = (xmlDocS1).getElementsByTagName("wps:Output");

		for (int i = 0; i < ListOutput.getLength(); i++) {

			Element ElementOutput = (Element) ListOutput.item(i);
			NodeList listMetadataOutput = ElementOutput.getElementsByTagName("ows:Metadata");
			for (int j = 0; j < listMetadataOutput.getLength(); j++) {

				Element ElementMetadataOutput = (Element) listMetadataOutput.item(j);
				String roleOutput = ElementMetadataOutput.getAttribute("xlink:role");
				String refOutput = ElementMetadataOutput.getAttribute("xlink:href");

				if (roleOutput.equals("http://www.opengis.net/spec/wps/2.0/def/concept_Output")) {
					ConceptsOutService.add(refOutput);
				}
			}
		}

		// **** Calculation of similarities Max (Input service,Input request) ****

		for (String elemInService : ConceptsInService) {
			maxSimC1C2In = 0;
			for (String elemInRequete : ConceptsRequeteIN) {

				SimPartialIn = Concepts_Similarity_Calculation.SimCalc("./data/DataOntology.owl", elemInService,
						baseOntoData + elemInRequete);
				if (SimPartialIn > maxSimC1C2In) {
					maxSimC1C2In = SimPartialIn;
				}
			}
			ListSimMaxInService.add(String.valueOf(maxSimC1C2In));
		}

		// **** Calculation of similarities Max (Output request, Output Service) ****

		for (String elemOutRequete : ConceptsRequeteOut) {
			maxSimC1C2Out = 0;
			for (String elemOutService : ConceptsOutService) {

				SimPartialOut = Concepts_Similarity_Calculation.SimCalc("./data/DataOntology.owl",
						baseOntoData + elemOutRequete, elemOutService);
				if (SimPartialOut > maxSimC1C2Out) {
					maxSimC1C2Out = SimPartialOut;
				}
			}
			ListSimMaxOutRequest.add(String.valueOf(maxSimC1C2Out));
		}

		// ********* Calculation of the sum of similarities Inputs *************

		for (String elemIn : ListSimMaxInService) {
			SommeSimIn = SommeSimIn + Float.parseFloat(elemIn);
		}

		// *********Calculation of the sum of similarities Outputs *************

		for (String elemOut : ListSimMaxOutRequest) {
			SommeSimOut = SommeSimOut + Float.parseFloat(elemOut);
		}

		// ********* Calculation of the Inputs similarity score *************

		if (ListSimMaxInService.size() != 0) {
			SimIn = SommeSimIn / (ListSimMaxInService.size());
		} else if ((ConceptsInService.size() == 0) || (ConceptsRequeteIN.size() == 0)) {
			SimIn = 0;
		}

		// *********Calculation of the Outputs similarity score *************
		if (ListSimMaxOutRequest.size() != 0) {
			SimOut = SommeSimOut / (ListSimMaxOutRequest.size());
		} else if ((ConceptsOutService.size() == 0) || (ConceptsRequeteOut.size() == 0)) {
			SimOut = 0;
		}

		// ********* Calculation of global similarity *************

		simGlo = (SimIn + SimOut) / 2;

		simList.add(ServiceIdentifier);
		simList.add(Float.toString(SimIn));
		simList.add(Float.toString(SimOut));
		simList.add(Float.toString(simGlo));

		return simList;
	}

}
