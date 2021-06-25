
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.BasicConfigurator;
import org.xml.sax.SAXException;

import Functional_matching_calculation.Functionality_fileAnalyzer_Calculation;
import Functional_matching_calculation.InOut_fileAnalyzer_Calculation;
import QoS_Calculation.QoS_FileAnalyzer_Calculation;
import Recommandation_calculation.ScoreRecommandation;

// This class is about creating a graphical interface for SWG discovery
@SuppressWarnings("serial")
public class Interface extends JFrame {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		BasicConfigurator.configure();
		Interface InterfaceInstance;
		InterfaceInstance = new Interface();

	}

	// ***********Declaration of variables**********************
	// *********************************************************

	String RequestFunctionnality;
	String RequestInputs;
	String RequestOutputs;
	float Threshold = 0;
	float WeightObjSubj = 0;
	float WeightFuncNFunc = 0;
	String ServiceNameInOut;
	Container container1 = new Container();
	Container container2 = new Container();
	Container container3 = new Container();
	JComboBox<String> combo_Functionality = new JComboBox<String>();
	JLabel label = new JLabel("Functionality request");
	JComboBox<String> combo_Input = new JComboBox<String>();
	JComboBox<String> combo_Output = new JComboBox<String>();
	JComboBox<String> combo_threshold_Functionality = new JComboBox<String>();
	JComboBox<String> combo_threshold_InOut = new JComboBox<String>();
	JTextField text_weight_SubjObj = new JTextField();
	JTextField text_weight_FuncNonFunc = new JTextField();

	JLabel label_Input = new JLabel("Inputs request");
	JLabel label_Output = new JLabel("Outputs request");
	JLabel label_Threshold_functionnality = new JLabel("Acceptance threshold");
	JLabel label_Threshold_InOut = new JLabel("Acceptance threshold");
	JLabel label_weight_SubjObj = new JLabel("Weight between objective and subjective properties");
	JLabel label_weight_FuncNonFunc = new JLabel("Weight between functional and non-functional score");

	JButton boutonFunctionality = new JButton(
			new CalculFunctionnalityScoreAction(this, "Calculate functionality score"));
	JButton boutonIn = new JButton(new AddInAction(this, "AddIn"));
	JButton boutonOut = new JButton(new AddOutAction(this, "AddOut"));
	JButton boutonSimInOut = new JButton(new CalculInOutScoreAction(this, "Calculate inputs/outputs score"));
	JButton boutonNonFunctional = new JButton(
			new CalculRecommandationScoreAction(this, "Calculate recommandation score"));

	JLabel labelInSelected = new JLabel("<html><br/> No inputs selected <br/> </html>");
	JLabel labelOutSelected = new JLabel("<html><br/> No outputs selected<br/> </html>");

	List<String> list_ConceptIn_Requete = new ArrayList<String>();
	List<String> list_ConceptOut_Requete = new ArrayList<String>();
	List<List<String>> list_sim_functionality_accepted = new ArrayList<List<String>>();
	List<List<String>> list_sim_InOuts_accepted = new ArrayList<List<String>>();

	public Interface() {

		this.setTitle("Request");
		this.setSize(800, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		container1.setLayout(new BorderLayout());
		container2.setLayout(new BorderLayout());
		container3.setLayout(new BorderLayout());

		// Table containing examples of service functionalities

		String[] tab = { "Distance_calculation_service", "Direct_geocoding_service" };

		// Table containing examples of inputs and outputs of services

		String[] tab2 = { "Distance", "EuclideanDistance", "SourceLocation", "Impedance", "SpatialEntity",
				"GeographicCoordinates" };

		// *****************************************
		combo_Functionality = new JComboBox<String>(tab);
		combo_Functionality.addActionListener(new ItemAction());
		combo_Functionality.setPreferredSize(new Dimension(400, 20));
		combo_Functionality.setForeground(Color.blue);
		// *****************************************
		combo_Input = new JComboBox<String>(tab2);
		combo_Input.addActionListener(new ItemAction2());
		combo_Input.setPreferredSize(new Dimension(400, 20));
		combo_Input.setForeground(Color.blue);
		// ***************************************
		combo_Output = new JComboBox<String>(tab2);
		combo_Output.addActionListener(new ItemAction3());
		combo_Output.setPreferredSize(new Dimension(400, 20));
		combo_Output.setForeground(Color.blue);

		// *****************************************

		String[] tab3 = { "Strict=1", "Medium=0.5", "Fuzzy=0" };
		combo_threshold_Functionality = new JComboBox<String>(tab3);
		combo_threshold_Functionality.addActionListener(new ItemAction4());
		combo_threshold_Functionality.setPreferredSize(new Dimension(400, 20));
		combo_threshold_Functionality.setForeground(Color.blue);

		String[] tab4 = { "Strict=1", "StrictToMedium=0.7", "Medium=0.5" };
		combo_threshold_InOut = new JComboBox<String>(tab4);
		combo_threshold_InOut.addActionListener(new ItemAction5());
		combo_threshold_InOut.setPreferredSize(new Dimension(400, 20));
		combo_threshold_InOut.setForeground(Color.blue);

		// *****************************************

		text_weight_SubjObj.setPreferredSize(new Dimension(400, 20));
		text_weight_SubjObj.setForeground(Color.blue);

		// *****************************************

		text_weight_FuncNonFunc.setPreferredSize(new Dimension(400, 20));
		text_weight_FuncNonFunc.setForeground(Color.blue);

		JPanel top = new JPanel(new GridBagLayout());

		JPanel panel = new JPanel();
		panel.add(label);
		panel.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		top.add(panel, gbc, 0);

		JPanel panel2 = new JPanel();
		panel2.add(combo_Functionality);
		panel2.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridwidth = GridBagConstraints.REMAINDER;
		gbc2.weightx = 1;
		gbc2.fill = GridBagConstraints.VERTICAL;
		top.add(panel2, gbc2, 1);

		JPanel panel3 = new JPanel();
		panel3.add(label_Threshold_functionnality);
		panel3.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.gridwidth = GridBagConstraints.REMAINDER;
		gbc3.weightx = 1;
		gbc3.fill = GridBagConstraints.VERTICAL;
		top.add(panel3, gbc3, 2);

		JPanel panel4 = new JPanel();
		panel4.add(combo_threshold_Functionality);
		panel4.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc4 = new GridBagConstraints();
		gbc4.gridwidth = GridBagConstraints.REMAINDER;
		gbc4.weightx = 1;
		gbc4.fill = GridBagConstraints.VERTICAL;
		top.add(panel4, gbc4, 3);

		JPanel panel5 = new JPanel();
		panel5.add(boutonFunctionality);
		panel5.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc5 = new GridBagConstraints();
		gbc5.gridwidth = GridBagConstraints.REMAINDER;
		gbc5.weightx = 1;
		gbc5.fill = GridBagConstraints.VERTICAL;
		top.add(panel5, gbc5, 4);

		// ***********Container 2******************

		JPanel topINOUT = new JPanel(new GridBagLayout());

		JPanel panel2_1 = new JPanel();
		panel2_1.add(label_Input);
		panel2_1.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc2_1 = new GridBagConstraints();
		gbc2_1.gridwidth = GridBagConstraints.REMAINDER;
		gbc2_1.weightx = 1;
		gbc2_1.fill = GridBagConstraints.VERTICAL;
		topINOUT.add(panel2_1, gbc2_1, 0);

		JPanel panel2_2 = new JPanel();
		panel2_2.add(combo_Input);
		panel2_2.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc2_2 = new GridBagConstraints();
		gbc2_2.gridwidth = GridBagConstraints.REMAINDER;
		gbc2_2.weightx = 1;
		gbc2_2.fill = GridBagConstraints.VERTICAL;
		topINOUT.add(panel2_2, gbc2_2, 1);

		JPanel panel2_3 = new JPanel();
		panel2_3.add(boutonIn);
		panel2_3.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc2_3 = new GridBagConstraints();
		gbc2_3.gridwidth = GridBagConstraints.REMAINDER;
		gbc2_3.weightx = 1;
		gbc2_3.fill = GridBagConstraints.VERTICAL;
		topINOUT.add(panel2_3, gbc2_3, 2);

		JPanel panel2_4 = new JPanel();
		panel2_4.add(labelInSelected);
		panel2_4.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc2_4 = new GridBagConstraints();
		gbc2_4.gridwidth = GridBagConstraints.REMAINDER;
		gbc2_4.weightx = 1;
		gbc2_4.fill = GridBagConstraints.VERTICAL;
		topINOUT.add(panel2_4, gbc2_4, 3);

		JPanel panel2_5 = new JPanel();
		panel2_5.add(label_Output);
		panel2_5.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc2_5 = new GridBagConstraints();
		gbc2_5.gridwidth = GridBagConstraints.REMAINDER;
		gbc2_5.weightx = 1;
		gbc2_5.fill = GridBagConstraints.VERTICAL;
		topINOUT.add(panel2_5, gbc2_5, 4);

		JPanel panel2_6 = new JPanel();
		panel2_6.add(combo_Output);
		panel2_6.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc2_6 = new GridBagConstraints();
		gbc2_6.gridwidth = GridBagConstraints.REMAINDER;
		gbc2_6.weightx = 1;
		gbc2_6.fill = GridBagConstraints.VERTICAL;
		topINOUT.add(panel2_6, gbc2_6, 5);

		JPanel panel2_7 = new JPanel();
		panel2_7.add(boutonOut);
		panel2_7.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc2_7 = new GridBagConstraints();
		gbc2_7.gridwidth = GridBagConstraints.REMAINDER;
		gbc2_7.weightx = 1;
		gbc2_7.fill = GridBagConstraints.VERTICAL;
		topINOUT.add(panel2_7, gbc2_7, 6);

		JPanel panel2_8 = new JPanel();
		panel2_8.add(labelOutSelected);
		panel2_8.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc2_8 = new GridBagConstraints();
		gbc2_8.gridwidth = GridBagConstraints.REMAINDER;
		gbc2_8.weightx = 1;
		gbc2_8.fill = GridBagConstraints.VERTICAL;
		topINOUT.add(panel2_8, gbc2_8, 7);

		JPanel panel2_9 = new JPanel();
		panel2_9.add(combo_threshold_InOut);
		panel2_9.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc2_9 = new GridBagConstraints();
		gbc2_9.gridwidth = GridBagConstraints.REMAINDER;
		gbc2_9.weightx = 1;
		gbc2_9.fill = GridBagConstraints.VERTICAL;
		topINOUT.add(panel2_9, gbc2_9, 8);

		JPanel panel2_10 = new JPanel();
		panel2_10.add(label_Threshold_InOut);
		panel2_10.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc2_10 = new GridBagConstraints();
		gbc2_10.gridwidth = GridBagConstraints.REMAINDER;
		gbc2_10.weightx = 1;
		gbc2_10.fill = GridBagConstraints.VERTICAL;
		topINOUT.add(panel2_10, gbc2_10, 9);

		JPanel panel2_11 = new JPanel();
		panel2_11.add(boutonSimInOut);
		panel2_11.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc2_11 = new GridBagConstraints();
		gbc2_11.gridwidth = GridBagConstraints.REMAINDER;
		gbc2_11.weightx = 1;
		gbc2_11.fill = GridBagConstraints.VERTICAL;
		topINOUT.add(panel2_11, gbc2_11, 10);

		// *******container3************
		JPanel topNF = new JPanel(new GridBagLayout());

		JPanel panel3_2 = new JPanel();
		panel3_2.add(label_weight_SubjObj);
		panel3_2.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc3_2 = new GridBagConstraints();
		gbc3_2.gridwidth = GridBagConstraints.REMAINDER;
		gbc3_2.weightx = 1;
		gbc3_2.fill = GridBagConstraints.VERTICAL;
		topNF.add(panel3_2, gbc3_2, 0);

		JPanel panel3_1 = new JPanel();
		panel3_1.add(text_weight_SubjObj);
		panel3_1.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc3_1 = new GridBagConstraints();
		gbc3_1.gridwidth = GridBagConstraints.REMAINDER;
		gbc3_1.weightx = 1;
		gbc3_1.fill = GridBagConstraints.VERTICAL;
		topNF.add(panel3_1, gbc3_1, 1);

		JPanel panel3_4 = new JPanel();
		panel3_4.add(label_weight_FuncNonFunc);
		panel3_4.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc3_4 = new GridBagConstraints();
		gbc3_4.gridwidth = GridBagConstraints.REMAINDER;
		gbc3_4.weightx = 1;
		gbc3_4.fill = GridBagConstraints.VERTICAL;
		topNF.add(panel3_4, gbc3_4, 2);

		JPanel panel3_3 = new JPanel();
		panel3_3.add(text_weight_FuncNonFunc);
		panel3_3.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc3_3 = new GridBagConstraints();
		gbc3_3.gridwidth = GridBagConstraints.REMAINDER;
		gbc3_3.weightx = 1;
		gbc3_3.fill = GridBagConstraints.VERTICAL;
		topNF.add(panel3_3, gbc3_3, 3);

		JPanel panel3_5 = new JPanel();
		panel3_5.add(boutonNonFunctional);
		panel3_5.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		GridBagConstraints gbc3_5 = new GridBagConstraints();
		gbc3_5.gridwidth = GridBagConstraints.REMAINDER;
		gbc3_5.weightx = 1;
		gbc3_5.fill = GridBagConstraints.VERTICAL;
		topNF.add(panel3_5, gbc3_5, 4);

		container1.add(top, BorderLayout.CENTER);
		container2.add(topINOUT, BorderLayout.CENTER);
		container3.add(topNF, BorderLayout.CENTER);
		this.setContentPane(container1);
		this.setVisible(true);
		this.revalidate();

	}

	// Classes and methods for the management of user events on the interface to
	// allow to define the treatments in response to these events
	class ItemAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			RequestFunctionnality = (String) combo_Functionality.getSelectedItem();

		}
	}
	// *****************************************

	class ItemAction2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			RequestInputs = (String) combo_Input.getSelectedItem();

		}
	}

	// *****************************************

	class ItemAction3 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			RequestOutputs = (String) combo_Output.getSelectedItem();

		}
	}

	// *****************************************

	class ItemAction4 implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (((String) combo_threshold_Functionality.getSelectedItem()).equals("Strict=1")) {
				Threshold = (float) 1.0;
			} else {
				if (((String) combo_threshold_Functionality.getSelectedItem()).equals("Medium=0.5")) {
					Threshold = (float) 0.5;
				} else if (((String) combo_threshold_Functionality.getSelectedItem()).equals("Fuzzy=0")) {
					Threshold = (float) 0.0;
				}
			}
		}
	}

	// ***********************************************

	class ItemAction5 implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (((String) combo_threshold_InOut.getSelectedItem()).equals("Strict=1")) {
				Threshold = (float) 1.0;
			} else {
				if (((String) combo_threshold_InOut.getSelectedItem()).equals("Medium=0.5")) {
					Threshold = (float) 0.5;
				} else if (((String) combo_threshold_InOut.getSelectedItem()).equals("StrictToMedium=0.7")) {
					Threshold = (float) 0.7;
				}
			}

		}
	}

	// ***The class containing the method that calls the matching score calculation
	// of the functionality.*****************
	class CalculFunctionnalityScoreAction extends AbstractAction {
		Interface fenetre;

		public CalculFunctionnalityScoreAction(Interface fenetre, String texte) {
			super(texte);
			this.fenetre = fenetre;
		}

		// ************************************************

		@Override
		public void actionPerformed(ActionEvent e) {

			if (RequestFunctionnality != null) {
				List<List<String>> list_sim_fonctionnality = new ArrayList<List<String>>();
				String result = "Discovery result: \n ------------\n ------------";
				int Simok = 0;

				try {
					long lStartTime = System.nanoTime();
					list_sim_fonctionnality = Functionality_fileAnalyzer_Calculation
							.CalculSimFunctionnality(RequestFunctionnality);
					long lEndTime = System.nanoTime();
					long output = lEndTime - lStartTime;
					long outputmiliseconde = output / 1000000;
					// System.out.println("Elapsed time in milliseconds: " + outputmiliseconde);
					long outputseconde = outputmiliseconde / 1000;
					System.out.println("\nElapsed time in seconds: " + outputseconde + "s \n");
					System.out.println("Discovery result: \n");
					for (List<String> elem : list_sim_fonctionnality) {

						if (Float.valueOf(elem.get(1)) >= Threshold) {
							list_sim_functionality_accepted.add(elem);
							Simok = 1;

							System.out.println("------------------");
							System.out.println("Service name: " + elem.get(2));
							System.out.println("Score functionnality: " + elem.get(1));
							System.out.println("------------------");
							result = result + "\n" + "Service name: " + elem.get(2) + "\n" + "Functionnality score: "
									+ elem.get(1) + "\n" + "------------";
						}

					}

				} catch (ParserConfigurationException e1) {

					e1.printStackTrace();
				} catch (SAXException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (Simok == 1) {
					System.out.println("*****************");
					JFrame f;
					f = new JFrame();
					JTextArea textArea = new JTextArea(15, 60);
					textArea.setText(result);
					textArea.setEditable(false);
					JScrollPane scrollPane = new JScrollPane(textArea);
					JOptionPane.showMessageDialog(f, scrollPane, "Discovery result", JOptionPane.INFORMATION_MESSAGE);

					Interface.this.ChangeView2();

				}

				else {
					JOptionPane.showMessageDialog(this.fenetre,
							result + "Sorry, there is no service that corresponds to your functionality request");

				}

			} else {
				JOptionPane.showMessageDialog(this.fenetre, "Please select a functionality concept");
			}
		}
	}
	// ************************************************

	class AddInAction extends AbstractAction {
		Interface fenetre;

		public AddInAction(Interface fenetre, String texte) {
			super(texte);
			this.fenetre = fenetre;
		}

		// ************************************************

		@Override
		public void actionPerformed(ActionEvent e) {
			String SelectedInputs = "";
			int AlreadySelected = 0;
			if (RequestInputs != null) {

				for (String elem : list_ConceptIn_Requete) {
					SelectedInputs = SelectedInputs + elem + "<br/>";
					if (elem.equals(RequestInputs)) {
						AlreadySelected = 1;
						JOptionPane.showMessageDialog(this.fenetre, "You have already selected this input concept");
					}
				}
				if (AlreadySelected == 0) {

					list_ConceptIn_Requete.add(RequestInputs);
					fenetre.labelInSelected.setText("<html>" + SelectedInputs + RequestInputs + "<br/>" + "</html>");

				}

			} else {
				JOptionPane.showMessageDialog(this.fenetre, "Please select a input concept");
			}
		}
	}

	// ***********************************
	class AddOutAction extends AbstractAction {
		Interface fenetre;

		public AddOutAction(Interface fenetre, String texte) {
			super(texte);
			this.fenetre = fenetre;
		}

		// ***********************************
		@Override
		public void actionPerformed(ActionEvent e) {
			String SelectedOutputs = "";
			int AlreadySelected = 0;
			if (RequestOutputs != null) {

				for (String elem : list_ConceptOut_Requete) {
					SelectedOutputs = SelectedOutputs + elem + "<br/>";
					if (elem.equals(RequestOutputs)) {
						JOptionPane.showMessageDialog(this.fenetre, "You have already selected this input concept");
						AlreadySelected = 1;
					}
				}
				if (AlreadySelected == 0) {
					list_ConceptOut_Requete.add(RequestOutputs);
					fenetre.labelOutSelected.setText("<html>" + SelectedOutputs + RequestOutputs + "<br/>" + "</html>");
				}
			} else {
				JOptionPane.showMessageDialog(this.fenetre, "Please select a input concept");
			}

		}

	}

	// ***The class containing the method that calls the matching score calculation
	// of the inputs and outputs which will represent the final functional score***
	class CalculInOutScoreAction extends AbstractAction {
		Interface fenetre;
		float simFunctional = 0;

		public CalculInOutScoreAction(Interface fenetre, String texte) {
			super(texte);
			this.fenetre = fenetre;
		}

		// **************************************************
		@Override
		public void actionPerformed(ActionEvent e) {
			int Simok = 0;
			String result = "Discovery result: \n ------------\n ------------";
			long lStartTime = System.nanoTime();
			for (List<String> elem : list_sim_functionality_accepted) {

				try {
					List<String> resPartiel = InOut_fileAnalyzer_Calculation.RequestDiscovery(elem.get(2),
							list_ConceptIn_Requete, list_ConceptOut_Requete);
					simFunctional = Float.valueOf(resPartiel.get(3));
					if (simFunctional >= Threshold) {

						list_sim_InOuts_accepted.add(resPartiel);
						Simok = 1;
						result = result + "\n" + "Service name: " + elem.get(2) + "\n" + "Functional score (InOut): "
								+ simFunctional + "\n" + "------------";

					}

				} catch (ParserConfigurationException | SAXException | IOException e1) {

					e1.printStackTrace();
				}

			}

			long lEndTime = System.nanoTime();
			long output = lEndTime - lStartTime;
			long outputmiliseconde = output / 1000000;
			// System.out.println("Elapsed time in milliseconds: " + outputmiliseconde);
			long outputseconde = outputmiliseconde / 1000;
			System.out.println("\nElapsed time in seconds: " + outputseconde + "s \n");
			if (Simok == 1) {

				System.out.println(result);
				System.out.println("*****************");

				JFrame f;
				f = new JFrame();
				JTextArea textArea = new JTextArea(15, 60);
				textArea.setText(result);
				textArea.setEditable(false);
				JScrollPane scrollPane = new JScrollPane(textArea);
				JOptionPane.showMessageDialog(f, scrollPane, "Discovery result", JOptionPane.INFORMATION_MESSAGE);

				Interface.this.ChangeView2();

			}

			else {
				JOptionPane.showMessageDialog(this.fenetre,
						result + "Sorry, there is no service that corresponds to your inputs/outputs request");

			}

			ChangeView3();

		}

	}

	// ********************************************************************

	// ***The class containing the method that calls the calculation of the
	// non-functional score and then the calculation of the global recommendation
	// score that includes the functional and non-functional scores
	// *********************************************************************
	class CalculRecommandationScoreAction extends AbstractAction {
		Interface fenetre;
		float simFunctional = 0;
		float simNonFunctional = 0;
		float simGlobalFANDNF = 0;

		public CalculRecommandationScoreAction(Interface fenetre, String texte) {
			super(texte);
			this.fenetre = fenetre;
		}

		// **************************************************
		@Override
		public void actionPerformed(ActionEvent e) {

			String result = "Final recommendation result: \n ------------\n ------------";
			for (List<String> elem : list_sim_InOuts_accepted) {

				simFunctional = Float.valueOf(elem.get(3));
				WeightObjSubj = Float.valueOf(text_weight_SubjObj.getText());
				WeightFuncNFunc = Float.valueOf(text_weight_FuncNonFunc.getText());
				try {
					simNonFunctional = QoS_FileAnalyzer_Calculation.NonFunScore(elem.get(0), WeightObjSubj);
					simGlobalFANDNF = ScoreRecommandation.RecScore(simFunctional, simNonFunctional, WeightFuncNFunc);

					result = result + "\n" + "Service name: " + elem.get(0) + "\n" + "Functional score (InOut): "
							+ simFunctional + "\n" + "Non-functional score: " + simNonFunctional + "\n"
							+ "Recommendation score : " + simGlobalFANDNF + "\n" + "------------";

				} catch (ParserConfigurationException | SAXException | IOException e1) {

					e1.printStackTrace();
				}
			}
			System.out.println(result);
			JFrame f;
			f = new JFrame();
			JTextArea textArea = new JTextArea(15, 60);
			textArea.setText(result);
			textArea.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(textArea);
			JOptionPane.showMessageDialog(f, scrollPane, "Recommendation result", JOptionPane.INFORMATION_MESSAGE);

		}

	}

	// *******************************************************************************************

	// A method to switch to the second interface view
	public void ChangeView2() {
		this.setContentPane(this.container2);
		this.revalidate();
	}

	// A method to switch to the third interface view
	public void ChangeView3() {
		this.setContentPane(this.container3);
		this.revalidate();
	}

}
