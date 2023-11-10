import { Component, OnInit } from '@angular/core';
import { Feature, Map, Overlay, View } from 'ol';
import { OSM, Vector } from 'ol/source'
import TileLayer from 'ol/layer/Tile';
import VectorLayer from 'ol/layer/Vector';
import { Point } from 'ol/geom';
import Style from 'ol/style/Style';
import Icon from 'ol/style/Icon';
import { fromLonLat } from 'ol/proj';
import * as bootstrap from 'bootstrap';

@Component({
  selector: 'app-view-estabelecimentos',
  templateUrl: './view-estabelecimentos.component.html',
  styleUrls: ['./view-estabelecimentos.component.css']
})
export class ViewEstabelecimentosComponent implements OnInit{
  public map!: Map

  ngOnInit(): void{
    this.map = new Map({
      layers:[
        new TileLayer({
          source: new OSM()
        })
      ],
      target: 'map',
      view: new View({
        center: fromLonLat([-53.8098809,-29.7001775]),
        zoom: 7,
        maxZoom: 18
      })
    });
    this.addMarkers();
  }

  private addMarkers(){
    const features = [];
    const f = new Feature({
      geometry: new Point(fromLonLat([-53.8098809, -29.7001775])),
    });
    f.setProperties({"id":1,"nome":"ola"});

    features.push(f);
    features.push(new Feature({
      geometry: new Point(fromLonLat([-53.7141824, -29.7211893])),
    }));
    features.push(new Feature({
      geometry: new Point(fromLonLat([-53.8301114, -29.688118])),
    }));
    features.push(new Feature({
      geometry: new Point(fromLonLat([-53.8146908, -29.6630133])),
    }));

    const layer = new VectorLayer({
        source: new Vector({
          features: features
        }),
        style: new Style({
            image: new Icon({
            anchor: [0.5, 1],
            crossOrigin: 'anonymous',
            src: 'https://docs.maptiler.com/openlayers/default-marker/marker-icon.png',
          })
        })
      });
      this.map.addLayer(layer);
  }

  teste(e: any){
    const coords = this.map.getEventCoordinate(e);
    const event_pixel = this.map.getPixelFromCoordinate(coords);
    const feature = this.map.forEachFeatureAtPixel(event_pixel,function(feature, layer){return feature;})
    console.log(feature,coords)
    if(feature){
      const popup = new Overlay({
        element: document.getElementById("popup") as HTMLElement,
        positioning: 'bottom-center',
        stopEvent: false
      });
      this.map.addOverlay(popup)
      popup.setPosition(coords)

      let popover = bootstrap.Popover.getInstance(popup.getElement() as HTMLElement);
      console.log(popover,coords,this.map.getPixelFromCoordinate(coords))

      if (popover) {
        popover.dispose();
      }
              //= new (window as any).bootstrap.Popover...
      popover = new bootstrap.Popover(popup.getElement() as HTMLElement, {
        animation: false,
        container: popup.getElement(),
        content: '<p>The location you clicked was:</p><code>' + feature.get("nome") + '</code>',
        html: true,
        placement: 'top',
        title: 'Welcome to OpenLayers',
      });
      popover.show();
    }
  }
}


