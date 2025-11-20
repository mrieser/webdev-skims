package com.simunto.skims;

import ch.sbb.matsim.analysis.skims.CalculateSkimMatrices;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.algorithms.TransportModeNetworkFilter;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.router.costcalculators.OnlyTimeDependentTravelDisutility;
import org.matsim.core.router.speedy.LeastCostPathTree;
import org.matsim.core.router.speedy.SpeedyGraph;
import org.matsim.core.router.speedy.SpeedyGraphBuilder;
import org.matsim.core.router.util.TravelDisutility;
import org.matsim.core.router.util.TravelTime;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.collections.CollectionUtils;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;

import java.io.IOException;
import java.util.Set;

public class Example {

	public static void main(String[] args) throws IOException {

		/*
		  Download the following files from https://svn.vsp.tu-berlin.de/repos/public-svn/matsim/scenarios/countries/de/berlin/berlin-v7.0/output/berlin-v7.0-10pct/

			- berlin-v7.0.output_events.xml.gz
			- berlin-v7.0.output_facilities.xml.gz
		 	- berlin-v7.0.output_network.xml.gz
		  - berlin-v7.0.output_transitSchedule.xml.gz

		  Download the zones from: https://hub.arcgis.com/datasets/esri-de-content::ortsteile-berlin/explore?location=52.504498%2C13.466129%2C11.24
		  as GeoJSON

		  The GeoJSON-File can be visualized e.g. with https://geojson.io/ or https://kepler.gl/demo

		  To calculate the skim matrices, the GeoJSON file needs to be converted into a Shape-File with coordinate system: EPSG:25832.
		  This could be done with QGis or other GIS tools.
		 */

		String zonesFilename = "/path/to/berlin/input/berlin_zones.shp";
		String zonesIdAttributeName = "OBJECTID";
		String facilitiesFilename = "/path/to/berlin-v7.0.output_facilities.xml.gz";
		String networkFilename = "/path/to/berlin-v7.0.output_network.xml.gz";
		String transitScheduleFilename = "/path/to/berlin-v7.0.output_transitSchedule.xml.gz";
		String eventsFilename = "/path/to/berlin-v7.0.output_events.xml.gz";
		String outputDirectory = "/path/to/berlin/skims/";

		CalculateSkimMatrices.main(new String[]{
						zonesFilename,
						zonesIdAttributeName,
						facilitiesFilename,
						networkFilename,
						transitScheduleFilename,
						eventsFilename,
						outputDirectory,
						"5", // 5 points per zone for sampling
						"6", // 6 threads
						"07:00;08:00", // car: average travel time between 7 am and 8 am
						"07:00;08:00", // pt: average travel time between 7 am and 8 am
						"car,pt" // calculate skims for both car and public-transport
				}
		);
	}

}
