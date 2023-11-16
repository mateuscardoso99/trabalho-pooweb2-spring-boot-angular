import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Feature, Map, Overlay, View } from 'ol';
import { OSM, Vector } from 'ol/source'
import TileLayer from 'ol/layer/Tile';
import VectorLayer from 'ol/layer/Vector';
import { Point } from 'ol/geom';
import Style from 'ol/style/Style';
import Icon from 'ol/style/Icon';
import { fromLonLat } from 'ol/proj';
import * as bootstrap from 'bootstrap';
import { EstabelecimentoService } from 'src/app/app-core/service/estabelecimento.service';
import { EstabelecimentoDto } from 'src/app/app-core/dto/EstabelecimentoDto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-estabelecimentos',
  templateUrl: './view-estabelecimentos.component.html',
  styleUrls: ['./view-estabelecimentos.component.css']
})
export class ViewEstabelecimentosComponent implements OnInit{
  public map!: Map
  public estabelecimentos: EstabelecimentoDto[] = [];

  constructor(
    private estabelecimentoService: EstabelecimentoService, 
    private el: ElementRef, 
    private renderer: Renderer2, 
    private router: Router
  ){}

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
    
    this.estabelecimentoService.findAll().subscribe({
      next: (resp: any)=> {
        this.estabelecimentos = resp.data as EstabelecimentoDto[]
        this.addMarkers();
      },
      error: err => {
        console.log(err)
      }
    })
  }

  ngAfterViewInit() {
    const estabalecimentoMapa = (<HTMLElement>this.el.nativeElement).querySelector('#map');
    this.renderer.listen(estabalecimentoMapa, 'click', (event) => {
      if(event.target.id.startsWith('estab_')){
        this.realizarPedido(event.target.id.substring(6))
      }
    });
  }

  private addMarkers(){
    const features:any = [];

    this.estabelecimentos.forEach(estab => {
      const f = new Feature({
        geometry: new Point(fromLonLat([+estab.endereco.longitude, +estab.endereco.latitude])),
      });
      f.setProperties({
        "id": estab.id,
        "nome": estab.nome,
        "horario": estab.horarioFuncionamento
      });
      
      features.push(f)
    })

    const layer = new VectorLayer({
        source: new Vector({
          features: features
        }),
        style: new Style({
            image: new Icon({
            anchor: [0.5, 1],
            crossOrigin: 'anonymous',
            src: '../../../../assets/img/map-marker-icon.png',
          })
        })
      });
      this.map.addLayer(layer);
  }

  onMapClick(e: any){
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
        content: '<p>Horário de funcionamento:</p><code>' + feature.get("horario") + '</code>'+"<br><div class='estab_mapa btn btn-sm btn-primary mt-2' id=estab_" + feature.get("id") + ">Fazer pedido</div>",
        html: true,
        placement: 'top',
        title: feature.get("nome"),
      });
      popover.show();
    }
  }

  realizarPedido(idEstab: number){
    console.log(idEstab)
    this.router.navigate(['/usuario/cadastro-pedido'],{queryParams: {idEstab}})
  }
}


