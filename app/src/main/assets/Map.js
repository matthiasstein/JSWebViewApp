require([
    "esri/map",
    "esri/toolbars/draw",
    "esri/graphic",

    "esri/layers/FeatureLayer",

    "esri/symbols/SimpleMarkerSymbol",
    "esri/symbols/SimpleLineSymbol",
    "esri/symbols/SimpleFillSymbol",

    "esri/tasks/query",
    "esri/tasks/QueryTask",
    "esri/Color",


    "dojo/domReady!"
], function(Map, Draw, Graphic, FeatureLayer, SimpleMarkerSymbol, SimpleLineSymbol, SimpleFillSymbol, Query, QueryTask, Color) {
    var toolbar, selectionToolbar;
    var featureServiceURL="http://services2.arcgis.com/lKwB42uXpb8Mwu4v/arcgis/rest/services/POIs_Wattenscheid/FeatureServer/8";
    var map = this.map = new Map("mapDiv", {
        center: [-56.049, 38.485],
        zoom: 3,
        basemap: "streets"
    });
    map.on("load", createToolbar);

    //var featureLayer = new FeatureLayer("http://services2.arcgis.com/lKwB42uXpb8Mwu4v/arcgis/rest/services/geschaefte/FeatureServer/0");
    var featureLayer = new FeatureLayer(featureServiceURL);
    var selectionSymbol =new SimpleMarkerSymbol(SimpleMarkerSymbol.STYLE_CIRCLE, 8,
                                   new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID,
                                   new Color([0,0,0]), 1),
                                   new Color([0,200,0]));
    featureLayer.setSelectionSymbol(selectionSymbol);


    var startExtent = new esri.geometry.Extent(7.05, 51.25, 7.45, 51.65,
        new esri.SpatialReference({
            wkid: 4326
        }));

    map.setExtent(startExtent);
    map.addLayer(featureLayer);

    function changeBasemap(basemap) {
        this.map.setBasemap(basemap)
    }

    function activateTool(mode) {
        toolbar.activate(Draw[mode]);
        //selectionToolbar.activate(Draw.EXTENT);
        map.hideZoomSlider();
    }

    function createToolbar(themap) {
        toolbar = new Draw(map);
        toolbar.on("draw-complete", addToMap);
    }

    function addToMap(evt) {
        var symbol;
        toolbar.deactivate();
        map.showZoomSlider();
        switch (evt.geometry.type) {
            case "point":
            case "multipoint":
                symbol = new SimpleMarkerSymbol();
                break;
            case "polyline":
                symbol = new SimpleLineSymbol();
                break;
            default:
                symbol = new SimpleFillSymbol();
                break;
        }
        var graphic = new Graphic(evt.geometry, symbol);
        map.graphics.add(graphic);
        queryFeatures(graphic);
    }

    function queryFeatures(inputGraphic){
    		var queryTask = new QueryTask(featureServiceURL);
    		var query = new Query();
    		query.outFields = ["*"];
    		query.returnGeometry = true;
    		query.geometry = inputGraphic.geometry;
    		query.spatialRelationship = Query.SPATIAL_REL_INTERSECTS;
    		//queryTask.executeForCount(query, showCountResult);
    		queryTask.execute(query,showFeatureResults);
            featureLayer.selectFeatures(query,
                                  FeatureLayer.SELECTION_NEW);
    	}

    	function showCountResult(count){
            map.graphics.clear();
            Android.showToast(count+" features selected");
        }

    	//This method will be use soon...
    	function showFeatureResults(featureSet) {
    		//remove all graphics on the maps graphics layer
    		map.graphics.clear();

    		//Performance enhancer - assign featureSet array to a single variable.
    		var resultFeatures = featureSet.features;

            var jsonFeatures =[];

    		//Loop through each feature returned
    		for (var i=0, il=resultFeatures.length; i<il; i++) {
    			//Get the current feature from the featureSet.
    			//Feature is a graphic
    			var graphic = resultFeatures[i];
    			jsonFeatures.push(dojo.toJson(graphic.toJson()));
    		}
    		Android.handleJsonFeatures(jsonFeatures);
    	}

    window.changeBasemap = changeBasemap;
    window.activateTool = activateTool;
});