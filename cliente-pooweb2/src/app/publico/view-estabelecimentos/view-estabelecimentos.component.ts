import { Component, OnInit } from '@angular/core';
import { Feature, Map, View } from 'ol';
import { OSM, Vector } from 'ol/source'
import TileLayer from 'ol/layer/Tile';
import * as olProj from 'ol/proj';
import VectorLayer from 'ol/layer/Vector';
import { Point } from 'ol/geom';
import Style from 'ol/style/Style';
import Icon from 'ol/style/Icon';

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
        center: olProj.fromLonLat([-53.8098809,-29.7001775]),
        zoom: 7,
        maxZoom: 18
      })
    });
    this.addMarkers();
  }

  private addMarkers(){
    const features = [];
    features.push(new Feature({
      geometry: new Point(olProj.fromLonLat([-53.8098809, -29.7001775])),
    }));
    features.push(new Feature({
      geometry: new Point(olProj.fromLonLat([-53.7141824, -29.7211893])),
    }));
    features.push(new Feature({
      geometry: new Point(olProj.fromLonLat([-53.8301114, -29.688118])),
    }));
    features.push(new Feature({
      geometry: new Point(olProj.fromLonLat([-53.8146908, -29.6630133])),
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

}
