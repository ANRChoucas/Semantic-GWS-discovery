package Functional_matching_calculation;

import java.io.InputStream;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntTools;
import org.apache.jena.ontology.OntTools.Path;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.Filter;

//A class containing proposed methods for the calculation of semantic similarity between two concepts in ontology
@SuppressWarnings("deprecation")
public class Concepts_Similarity_Calculation {

	// ** the method for calculating the similarity between two concepts **
	// ** We differentiate the type of concepts to match (functionality, input or
	// output) when calling this function **
	public static float SimCalc(String inputFileName, String C1, String C2) {

		float Sim = 0;
		float N3 = 0;
		float N2 = 0;
		float N1 = 0;

		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		InputStream in = FileManager.get().open(inputFileName);
		model.read(in, "RDF/XML");

		OntClass OntClassC1 = model.getOntClass(C1);
		OntClass OntClassC2 = model.getOntClass(C2);
		OntClass PrincipaleMother = model.getOntClass(getMotherClass(inputFileName));
		OntClass subsumant = CommunSubSumant(inputFileName, C1, C2);

		if (OntClassC2.toString() == OntClassC1.toString()) {

			Sim = 1;
		} else {
			if (OntClassC2.hasEquivalentClass(OntClassC1)) {

				Sim = 1;
			} else {
				if (OntClassC1.hasSubClass(OntClassC2)) {
					N3 = FindJenaDistance(inputFileName, subsumant, PrincipaleMother) + 1; // +1 in order to a
																							// consideration of the root
																							// of ontology
					N2 = FindJenaDistance(inputFileName, OntClassC1, subsumant);
					N1 = FindJenaDistance(inputFileName, OntClassC2, subsumant);
					Sim = (2 * N3) / (N1 + N2 + (2 * N3));
				} else {
					Sim = 0;
				}
			}
		}

		return Sim;
	}

	// **** A method for computing the distance between two concepts in an ontology
	// *****
	public static int FindJenaDistance(String inputFileName, OntClass fromSubClass, OntClass toSuperClass) {
		int longueur = 0;

		if (fromSubClass != toSuperClass) {

			if (fromSubClass.getSuperClass() != toSuperClass) {

				OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
				InputStream in = FileManager.get().open(inputFileName);
				model.read(in, "RDF/XML");

				@SuppressWarnings("unchecked")
				Path path = OntTools.findShortestPath(model, fromSubClass, toSuperClass, Filter.any);

				if (path != null) {
					longueur = path.size();
				}
			} else {
				longueur = 1;
			}
		}
		return longueur;

	}

	// A method to Calculate the smallest common sumbsumant

	public static OntClass CommunSubSumant(String inputFileName, String C1, String C2) {

		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		InputStream in = FileManager.get().open(inputFileName);
		model.read(in, "RDF/XML");
		OntClass c1 = model.getOntClass(C1);
		OntClass c2 = model.getOntClass(C2);
		OntClass subsumant = OntTools.getLCA(model, c1, c2);
		return subsumant;

	}

	// A method to return the main mother class of the ontology which is a direct
	// subclass of the root class Thing depending on the used ontology
	public static String getMotherClass(String inputFileName) {
		String MotherClass = null;
		if (inputFileName.equals("./data/DataOntology.owl")) {

			MotherClass = "http://www.semanticweb.org/halilali/ontologies/2020/0/untitled-ontology-25#SettingsInOut";
		} else if (inputFileName.equals("./data/ServiceOntology.owl")) {
			MotherClass = "http://www.semanticweb.org/home/ontologies/2018/6/untitled-ontology-6#Geogaphic_service_(GS)";

		}
		return MotherClass;
	}

}
