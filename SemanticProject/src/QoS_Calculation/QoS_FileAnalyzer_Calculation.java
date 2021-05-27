package QoS_Calculation;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//** a class containing the method for non functional score calculation **
public class QoS_FileAnalyzer_Calculation {

	// ***** The method to parse the non functional description file and
	// retrieve informations related to the non functional properties and calculate
	// the non functional score ******

	public static float NonFunScore(String ServiceName, Float Alpha)
			throws ParserConfigurationException, SAXException, IOException {
		// Variables Declaration
		int nbReports = 0;
		int cptSubjective = 0;
		float sumSubjective = 0;
		float avgSubjective = 0;
		float sumService_rs = 0;
		float sumSuplier_rs = 0;
		float value_rs = 0;
		float value_rs_Pourc = 0;
		float avgSubjectiveMulAlphaMinOne = 0;
		// *****************************
		int nbReportsObj = 0;
		int cptReportGeneralObj = 0;
		float Cost = 0;
		float Security = 0;
		float Availability = 0;
		float Performance = 0;
		float Capacity = 0;
		float MaxCapacity = 0;
		float ValGen_FLOAT = 0;
		float sumGeneral = 0;
		// *****************************
		int nbReportsGeoSpa = 0;
		int cptReportGeoSpa = 0;
		float Scope = 0;
		float Currency = 0;
		float Precision = 0;
		float Richness = 0;
		float Lineage = 0;
		float sumGeoSpa = 0;
		float sumObjective;
		float avgObjective;
		float avgObjectiveMulAlpha;

		// ***********Analysis of the non-functional file and extraction of information
		// to calculate the non-functional score***********************

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder loader = factory.newDocumentBuilder();
		Document xmlDocS1 = loader.parse("./data/NonFunctional_Description/" + "NF" + ServiceName);

		// *******************Subjective properties**************************
		NodeList ListSubjectivePropreties = (xmlDocS1).getElementsByTagName("SubjectiveProprties");
		Element SubProp = (Element) ListSubjectivePropreties.item(0);
		NodeList listeDesReport = SubProp.getElementsByTagName("gmd:report");
		nbReports = listeDesReport.getLength();

		while (cptSubjective < nbReports) {

			Element Report = (Element) listeDesReport.item(cptSubjective);
			NodeList listAnchourSubjidentifier = Report.getElementsByTagName("gmx:Anchor");
			Element ElmSubjAnchour = (Element) listAnchourSubjidentifier.item(0);
			String valueSubjAnchour_str = ElmSubjAnchour.getTextContent();

			NodeList listevalue = Report.getElementsByTagName("gmd:value");
			Element VAL_listevalue = (Element) listevalue.item(0);

			NodeList listerecords = VAL_listevalue.getElementsByTagName("gco:Record");
			Element Record1 = (Element) listerecords.item(0);
			String valueSubj_str = Record1.getTextContent();

			if (valueSubjAnchour_str.equals(("service_rs"))) {
				value_rs = Float.valueOf(valueSubj_str);
				value_rs_Pourc = value_rs / 5;
				sumService_rs = value_rs_Pourc;
			}
			if (valueSubjAnchour_str.equals(("suplier_rs"))) {
				value_rs = Float.valueOf(valueSubj_str);
				value_rs_Pourc = value_rs / 5;
				sumSuplier_rs = value_rs_Pourc;
			}

			cptSubjective++;

		}

		sumSubjective = sumService_rs + sumSuplier_rs;
		avgSubjective = sumSubjective / cptSubjective;
		avgSubjectiveMulAlphaMinOne = avgSubjective * (1 - Alpha);

		// *******************Subjective properties (End)**************************

		// *******************Objective properties*********************************
		NodeList ListObjectivePropreties = (xmlDocS1).getElementsByTagName("ObjectiveProprties");
		Element ObjProp = (Element) ListObjectivePropreties.item(0);

		// ******************General Objective properties*************************
		NodeList listeDesGeneral = ObjProp.getElementsByTagName("General");
		Element ElmGenral = (Element) listeDesGeneral.item(0);
		NodeList listeDesReportObj = ElmGenral.getElementsByTagName("gmd:report");
		nbReportsObj = listeDesReportObj.getLength();

		while (cptReportGeneralObj < nbReportsObj) {

			Element GenReport = (Element) listeDesReportObj.item(cptReportGeneralObj);
			NodeList listAnchourDeidentifier = GenReport.getElementsByTagName("gmx:Anchor");
			Element ElmAnchour = (Element) listAnchourDeidentifier.item(0);
			String valueAnchour_str = ElmAnchour.getTextContent();
			NodeList listValeurs = GenReport.getElementsByTagName("gmd:value");
			Element VALEUR = (Element) listValeurs.item(0);
			NodeList listGenrecords = VALEUR.getElementsByTagName("gco:Record");
			Element GenRecord1 = (Element) listGenrecords.item(0);
			String ValGen_str = GenRecord1.getTextContent();

			if (valueAnchour_str.equals(("cost"))) {

				switch (ValGen_str) {
				case "Free":
					ValGen_FLOAT = (float) 1;
					break;
				case "Low":
					ValGen_FLOAT = (float) 0.75;
					break;
				case "Medium":
					ValGen_FLOAT = (float) 0.5;
					break;
				case "High":
					ValGen_FLOAT = (float) 0.25;
					break;
				case "Very high":
					ValGen_FLOAT = (float) 0;
					break;
				default:
					ValGen_FLOAT = (float) 1;

				}
				Cost = ValGen_FLOAT;
			}

			if (valueAnchour_str.equals(("security"))) {

				switch (ValGen_str) {
				case "Insecure":
					ValGen_FLOAT = 0;
					break;
				case "Low":
					ValGen_FLOAT = (float) 0.25;
					break;
				case "Medium":
					ValGen_FLOAT = (float) 0.5;
					break;
				case "High":
					ValGen_FLOAT = (float) 0.75;
					break;
				case "Very high":
					ValGen_FLOAT = 1;
					break;
				default:

				}
				Security = ValGen_FLOAT;

			}

			if (valueAnchour_str.equals(("availability"))) {

				ValGen_FLOAT = Float.valueOf(ValGen_str);
				Availability = ValGen_FLOAT / 100;

			}

			if (valueAnchour_str.equals(("performance"))) {

				Float ValueOfPerformance = Float.valueOf(ValGen_str);

				if (ValueOfPerformance >= 0 && ValueOfPerformance <= 1) {
					ValGen_FLOAT = (float) 1;
				} else {

					if (ValueOfPerformance > 1 && ValueOfPerformance <= 2) {
						ValGen_FLOAT = (float) 0.75;
					} else {

						if (ValueOfPerformance > 2 && ValueOfPerformance <= 3) {
							ValGen_FLOAT = (float) 0.5;
						} else {

							if (ValueOfPerformance > 3 && ValueOfPerformance <= 4) {
								ValGen_FLOAT = (float) 0.25;
							} else {
								if (ValueOfPerformance > 4) {
									ValGen_FLOAT = (float) 0;
								}
							}
						}
					}
				}
				Performance = ValGen_FLOAT;

			}

			if (valueAnchour_str.equals(("Maxcapacity"))) {

				ValGen_FLOAT = Float.valueOf(ValGen_str);
				MaxCapacity = ValGen_FLOAT;

			}

			if (valueAnchour_str.equals(("capacity"))) {

				ValGen_FLOAT = Float.valueOf(ValGen_str);
				Capacity = ValGen_FLOAT / MaxCapacity;

			}

			cptReportGeneralObj++;
		}
		sumGeneral = Cost + Security + Availability + Performance + Capacity;

		// ******************General Objective properties (End)*******************

		// ******************Geospatial Objective properties**********************
		// ******************Geocoding properties*********************************
		NodeList listeDesGeoSpa = ObjProp.getElementsByTagName("Geospatial");
		Element ElmGeoSpa = (Element) listeDesGeoSpa.item(0);
		NodeList listeDesReportGeoSpa = null;
		if (listeDesGeoSpa.getLength() > 0) {
			listeDesReportGeoSpa = ElmGeoSpa.getElementsByTagName("gmd:report");
			nbReportsGeoSpa = listeDesReportGeoSpa.getLength();
			while (cptReportGeoSpa < nbReportsGeoSpa) {
				Element GeoSpaReport = (Element) listeDesReportGeoSpa.item(cptReportGeoSpa);
				NodeList listAnchourDeidentifierGeoSpa = GeoSpaReport.getElementsByTagName("gmx:Anchor");
				Element ElmAnchourGeoSpa = (Element) listAnchourDeidentifierGeoSpa.item(0);
				String valueAnchourGeoSpa_str = ElmAnchourGeoSpa.getTextContent();

				NodeList listValeursGeoSpa = GeoSpaReport.getElementsByTagName("gmd:value");
				Element VALEURGeoSpa = (Element) listValeursGeoSpa.item(0);
				NodeList listGeoSparecords = VALEURGeoSpa.getElementsByTagName("gco:Record");
				Element GeoSpaRecord1 = (Element) listGeoSparecords.item(0);

				String ValGeoSpa_str = GeoSpaRecord1.getTextContent();
				Float ValGeoSpa_FLOAT = (float) 0;

				if (valueAnchourGeoSpa_str.equals(("scope"))) {

					switch (ValGeoSpa_str) {

					case "Communal":
						ValGeoSpa_FLOAT = (float) 0.25;
						break;
					case "Regional":
						ValGeoSpa_FLOAT = (float) 0.5;
						break;
					case "National":
						ValGeoSpa_FLOAT = (float) 0.75;
						break;
					case "Worldwide":
						ValGeoSpa_FLOAT = (float) 1;
						break;
					default:
						ValGeoSpa_FLOAT = (float) 0.25;
					}
					Scope = ValGeoSpa_FLOAT;
				}

				if (valueAnchourGeoSpa_str.equals(("currency"))) { // currency is the update frequency

					switch (ValGeoSpa_str) {

					case "Never":
						ValGeoSpa_FLOAT = (float) 0;
						break;
					case "Annual":
						ValGeoSpa_FLOAT = (float) 0.25;
						break;
					case "Monthly":
						ValGeoSpa_FLOAT = (float) 0.5;
						break;
					case "Weekly":
						ValGeoSpa_FLOAT = (float) 0.75;
						break;
					case "Daily":
						ValGeoSpa_FLOAT = (float) 1;
						break;
					default:
						ValGeoSpa_FLOAT = (float) 0;

					}

					Currency = ValGeoSpa_FLOAT;

				}

				if (valueAnchourGeoSpa_str.equals(("precision"))) {

					switch (ValGeoSpa_str) {

					case "Very low":
						ValGeoSpa_FLOAT = (float) 0;
						break;
					case "Low":
						ValGeoSpa_FLOAT = (float) 0.25;
						break;
					case "Medium":
						ValGeoSpa_FLOAT = (float) 0.5;
						break;
					case "Medium to fine":
						ValGeoSpa_FLOAT = (float) 0.75;
						break;
					case "Fine":
						ValGeoSpa_FLOAT = (float) 1;
						break;
					default:
						ValGeoSpa_FLOAT = (float) 0;

					}
					Precision = ValGeoSpa_FLOAT;

				}

				if (valueAnchourGeoSpa_str.equals(("richness"))) {

					switch (ValGeoSpa_str) {

					case "Very low":
						ValGeoSpa_FLOAT = (float) 0;
						break;

					case "Low":
						ValGeoSpa_FLOAT = (float) 0.25;
						break;
					case "Medium":
						ValGeoSpa_FLOAT = (float) 0.5;
						break;
					case "Rich":
						ValGeoSpa_FLOAT = (float) 0.75;
						break;
					case "Very rich":
						ValGeoSpa_FLOAT = (float) 1;
						break;
					default:
						ValGeoSpa_FLOAT = (float) 0;

					}
					Richness = ValGeoSpa_FLOAT;

				}

				if (valueAnchourGeoSpa_str.equals(("lineage"))) {

					switch (ValGeoSpa_str) {

					case "Low":
						ValGeoSpa_FLOAT = (float) 0.25;
						break;
					case "Medium":
						ValGeoSpa_FLOAT = (float) 0.5;
						break;

					case "Various":
						ValGeoSpa_FLOAT = (float) 1;
						break;
					default:
						ValGeoSpa_FLOAT = (float) 0.25;
					}
					Lineage = ValGeoSpa_FLOAT;

				}

				cptReportGeoSpa++;

			}

		}
		sumGeoSpa = Scope + Currency + Precision + Richness + Lineage;
		sumObjective = (sumGeoSpa + sumGeneral);
		avgObjective = sumObjective / (nbReportsGeoSpa + listeDesReportObj.getLength() - 1);
		avgObjectiveMulAlpha = avgObjective * Alpha;

		return avgObjectiveMulAlpha + avgSubjectiveMulAlphaMinOne;
	}

}
