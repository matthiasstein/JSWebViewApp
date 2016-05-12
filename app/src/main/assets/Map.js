require([
    "esri/map",
    "esri/toolbars/draw",
    "esri/graphic",

    "esri/layers/FeatureLayer",

    "esri/symbols/SimpleMarkerSymbol",
    "esri/symbols/SimpleLineSymbol",
    "esri/symbols/SimpleFillSymbol",

    "dojo/domReady!"
], function(Map, Draw, Graphic, FeatureLayer, SimpleMarkerSymbol, SimpleLineSymbol, SimpleFillSymbol) {
    var toolbar;
    var map = this.map = new Map("mapDiv", {
        center: [-56.049, 38.485],
        zoom: 3,
        basemap: "streets"
    });
    map.on("load", createToolbar);

    var featureLayer = new FeatureLayer("http://services2.arcgis.com/lKwB42uXpb8Mwu4v/arcgis/rest/services/geschaefte/FeatureServer/0");

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
    }

    window.changeBasemap = changeBasemap;
    window.activateTool = activateTool;
});