
import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit, faTrashAlt, faFolderOpen } from '@fortawesome/free-solid-svg-icons';
import { Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import {Component } from 'react';
import { FolderService } from './FolderServices';
import ReactDOM from 'react-dom';
import reportWebVitals from './reportWebVitals';
import AppItems from './AppItems';
import React from 'react';

class App extends Component {
  constructor(){
    super();
    this.state={
      folders:[],
      folderService: new FolderService(),
      modalInsert:false,
      deleteModal:false,
      modalType:'',
      verItems:false,
      form:{
        id:'',
        name: ''
      }
    }
    this.handlerVerItems= this.handlerVerItems.bind(this);
  }
  componentDidMount(){
    this.state.folderService.getFolders().then(data =>this.setState({folders:data}));

  }

  saveFolder(){
        this.state.folderService.saveFolder(this.state.form).then(response=>{
        this.modalInsert();
        this.componentDidMount();
      }
    )
  }

  selectFolder=(folder)=>{
    this.setState({
      modalType:'update',
      form:{
        id:folder.id,
        name:folder.name,
      }
    })
  }
  deleteFolder(){
    this.state.folderService.deleteFolder(this.state.form.id).then(response=>{
      this.setState({deleteModal:false});
      this.componentDidMount();
    })
  }
  updateFolder(){
      this.state.folderService.updateFolder(this.state.form).then(response=>{
      this.modalInsert();
      this.componentDidMount();
    })
  }

  openFolder(){
    this.setState({verItems:true});
  }
  handlerVerItems(opt){
    this.setState({verItems:opt});
  }

  handleChange=async e=>{
    e.persist();
    await this.setState({
      form:
      {
        ...this.state.form,
        [e.target.name]:e.target.value
      }
    })
  }
  modalInsert=()=>{
    this.setState({modalInsert: !this.state.modalInsert});
  }
  render(){
    const {form}= this.state;
    return (
      <div className="App">
        <table className="table ">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
            </tr>
          </thead>
          <tbody>
            {this.state.folders.map(folder => {
              return(
                <tr>
                  <td>{folder.id}</td>
                  <td>{folder.name}</td>
                  <td>
                    <button className="btn btn-primary" onClick={()=>{this.selectFolder(folder);this.openFolder()}}><FontAwesomeIcon icon={faFolderOpen}/></button>
                    {"   "}
                    <button className="btn btn-primary" onClick={()=>{this.selectFolder(folder);this.modalInsert()}}><FontAwesomeIcon icon={faEdit}/></button>
                    {"   "}
                    <button className="btn btn-danger" onClick={()=>{this.selectFolder(folder);this.setState({deleteModal:true})}}><FontAwesomeIcon icon={faTrashAlt}/></button>
                  
                </td>
                </tr>
              )
            })}
          </tbody>
        </table>

        <Modal isOpen={this.state.modalInsert}>
        <div className="Form-group">
        <label htmlFor="id">ID</label>
                    <input className="form-control" type="text" name="id" id="id" readOnly onChange={this.handleChange} value={form?form.id: ''}/>
                    <label htmlFor="name">Name</label>
                    <input className="Form-control" type="text" name="name" id="name" onChange={this.handleChange} value={form?form.name:''} />
                    <br />
        </div>
        <ModalFooter>
                  {this.state.modalType =='insert'?
                    <button className="btn btn-success" onClick={()=>this.saveFolder()}>
                    Add Folder
                    </button>: <button className="btn btn-primary" onClick={()=>this.updateFolder()}>
                    Update
                    </button>
                  }
                    <button className="btn btn-danger" onClick={()=>this.modalInsert()}>Cancel</button>
        </ModalFooter>

              
        </Modal>


        <Modal isOpen={this.state.deleteModal}>
            <ModalBody>
              Are you sure you want to delete this Folder {form && form.name}
            </ModalBody>
            <ModalFooter>
              <button className="btn btn-danger" onClick={()=>this.deleteFolder()}>Yes</button>
              <button className="btn btn-secundary" onClick={()=>this.setState({deleteModal: false})}>No</button>
            </ModalFooter>
          </Modal>

          <button className="btn btn-success" onClick={()=>{this.setState({form: null, modalType: 'insert'}); this.modalInsert()}} >Add Folder</button>
        {this.state.verItems ? <AppItems folder={this.state.form.id} handlerVerItems={this.handlerVerItems}/> : null}          
      </div>
     
    );
  }
}
export default App;
