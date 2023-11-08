export class EnderecoDto{
    id: number;
    cidade: string;
    uf: string;
    bairro: string;
    rua: string;
    numero: string;
    complemento: string;
    latitude: string;
    longitude: string;

    constructor(
        id: number,
        cidade: string,
        uf: string,
        bairro: string,
        rua: string,
        numero: string,
        complemento: string,
        latitude: string,
        longitude: string
    ){
        this.id = id;
        this.cidade = cidade;
        this.uf = uf;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}