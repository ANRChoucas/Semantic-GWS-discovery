package Recommandation_calculation;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

// A class containing the method for calculating the final recommendation score that include functional and non functional scores
public class ScoreRecommandation {

	// The method for final recommendation score that include functional and non
	// functional scores
	public static float RecScore(float ScoreFonctionnel, float ScoreNonFonctionnel, float Beta)

			throws ParserConfigurationException, SAXException, IOException {
		float ScoreRecommadation = 0;
		ScoreRecommadation = ((Beta * ScoreFonctionnel)) + ((1 - Beta) * ScoreNonFonctionnel);
		return ScoreRecommadation;

	}

}
