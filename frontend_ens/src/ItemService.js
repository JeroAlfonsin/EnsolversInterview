import axios from "axios";

export class ItemsService {

    getItems(idF){
        return axios.get("http://localhost:8080/items/"+ idF).then(response =>response.data);
    }

    saveItems(item){
        return axios.post("http://localhost:8080/items", item).then(response=>response.data);
    }

    updateItem(item){
        return axios.put("http://localhost:8080/items" , item).then(response=>response.data)
    }

    deleteItem(id){
        return axios.delete("http://localhost:8080/items/"+id).then(response=>response.data)
    }
}