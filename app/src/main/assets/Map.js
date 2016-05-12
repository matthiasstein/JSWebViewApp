require([
  "esri/map",
  "esri/toolbars/draw",
  "esri/graphic",

  "esri/symbols/SimpleMarkerSymbol",
  "esri/symbols/SimpleLineSymbol",
  "esri/symbols/SimpleFillSymbol",

  "dojo/domReady!"
], function(Map,Draw, Graphic,SimpleMarkerSymbol, SimpleLineSymbol, SimpleFillSymbol){
 var toolbar;
 var map = this.map = new Map("mapDiv", {
    center: [-56.049, 38.485],
    zoom: 3,
    basemap: "streets"
});
  map.on("load", createToolbar);


  function changeBasemap(basemap){
      this.map.setBasemap(basemap)
  }

  function activateTool() {
      toolbar.activate(Draw["FREEHAND_POLYGON"]);
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
  window.activateTool=activateTool;
});