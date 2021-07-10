import axios from "axios";

export class FolderService {

    getFolders(){
        return axios.get("http://localhost:8080/folders").then(response =>response.data);
    }

    saveFolder(folder){
        return axios.post("http://localhost:8080/folders", folder).then(response=>response.data);
    }

    updateFolder(folder){
        return axios.put("http://localhost:8080/folders" , folder).then(response=>response.data)
    }

    deleteFolder(id){
        return axios.delete("http://localhost:8080/folders/"+id).then(response=>response.data)
    }
}