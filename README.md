# Semantic-GWS-discovery

## Introduction

This project has been created  in the context of the Choucas project (<http://choucas.ign.fr/>) funded by the French National Research Agency (ANR) with the purpose of heterogeneous data integration and spatial reasoning for localizing victims in mountain area. The objective of the project presented here is to propose an approach to Geospatial Web Service (GWS) discovery through semantic annotation of Web Processing service (WPS) service descriptions.

## Machine configuration
To implement the proposed methods, we developed a system for SWG discovery implemented in ***Java***.  All the experiments were executed on a machine with the configuration bellow : 
- Intel i7-7820 Ocatacore processor running at 2.90GHz, 
- 16Gb RAM,
- 500Gb SSD storage disk. 

## Project organisation

The project is orgnized as follow:

1. **Src**: this directory contains the 4 java packages including a default package

  - **Default package**: contains the "Interface" class that creates a graphical interface (swing/AWT) for SWG discovery with some example concepts of annotating the functionality and I/O of GWS, in order to test the proposed approach. This class contains the main method, so you can run this class to test the proposed discovery approach. 

  - **Functional_matching_calculation package**: contains the classes for calculating the semantic similarity between two concepts in the ontology, for calculating the semantic similarity of functionalities between two services, and finally for calculating the semantic similarity of inputs and outputs between two services.

  - **QoS_Calculation package**: contains the class for calculating a QoS score (or non-functional score) of a service 

  - **Recommadation_calculation package**: contains a class for calculating the final recommendation score that includes functional and non-functional scores.

2. **data**: the data directory contains all the data used in this experimentation, namely the functional description files in the "Functional_Description" subdirectory and the non-functional description files in the "NonFunctional_Description" subdirectory, as well as the two domain ontologies, service ontology and data ontology, in order to semantically annotate the service functionality and the I/O of a service.
We underline that for the annotations of the input/output parameters we have annotated the mandatory input parameters (specified with the attribute minOccurs="1") and all the output parameters.

3. **lib** : since this project use several libraries, the used libraries are available in this lib directory. 


For further information, please contact Meriem HALILALI (<meriem-sabrine.halilali@univ-pau.fr>)


