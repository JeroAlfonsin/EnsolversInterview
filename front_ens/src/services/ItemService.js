import axios from 'axios';

export class ItemService{

  getAll(){
    return axios.get("http://localhost:8080/items").then(res => res.data);
  }

  saveItem(item){
    return axios.post("http://localhost:8080/items", item).then(res => res.data);
  }
}
