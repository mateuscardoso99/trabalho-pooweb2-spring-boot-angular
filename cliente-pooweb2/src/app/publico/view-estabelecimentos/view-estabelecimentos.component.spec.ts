import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewEstabelecimentosComponent } from './view-estabelecimentos.component';

describe('ViewEstabelecimentosComponent', () => {
  let component: ViewEstabelecimentosComponent;
  let fixture: ComponentFixture<ViewEstabelecimentosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewEstabelecimentosComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewEstabelecimentosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
