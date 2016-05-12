require([
  "esri/Map",
  "esri/views/MapView",
  "dojo/domReady!"
], function(Map, MapView){
  var map = this.map = new Map({
    basemap: "streets"
  });
  var view = new MapView({
    container: "viewDiv",  // Reference to the scene div created in step 5
    map: map,  // Reference to the map object created before the scene
    zoom: 4,  // Sets the zoom level based on level of detail (LOD)
    center: [15, 65]  // Sets the center point of view in lon/lat
  });
  function changeBasemap(){
      this.map.basemap = "topo";
  }
  window.changeBasemap = changeBasemap;
});