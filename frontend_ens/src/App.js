import logo from './logo.svg';
import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEdit, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import { Component } from 'react';
import { ItemsService } from './ItemService';
import { ThemeConsumer } from 'react-bootstrap/esm/ThemeProvider';

class App extends Component {
  state={
    items:[],
    itemService: new ItemsService(),
    modalInsert:false,
    deleteModal:false,
    modalType:'',
    form:{
      id:'',
      name: ''
    }
  }
  componentDidMount(){
    this.state.itemService.getItems().then(data =>this.setState({items:data}));

  }

  saveItem= async()=>{
    await this.state.itemService.saveItems(this.state.form).then(response=>{
        this.modalInsert();
        this.componentDidMount();
      }
    )
  }

  selectItem=(item)=>{
    this.setState({
      modalType:'update',
      form:{
        id:item.id,
        name:item.name,
      }
    })
  }
  deleteItem(){
    this.state.itemService.deleteItem(this.state.form.id).then(response=>{
      this.setState({deleteModal:false});
      this.componentDidMount();
    })
  }
  updateItem(){
      this.state.itemService.updateItem(this.state.form).then(response=>{
      this.modalInsert();
      this.componentDidMount();
    })
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
            {this.state.items.map(item => {
              return(
                <tr>
                  <td>{item.id}</td>
                  <td>{item.name}</td>
                  <td>
                  <button className="btn btn-primary" onClick={()=>{this.selectItem(item);this.modalInsert()}}><FontAwesomeIcon icon={faEdit}/></button>
                {"   "}
                <button className="btn btn-danger" onClick={()=>{this.selectItem(item);this.setState({deleteModal:true})}}><FontAwesomeIcon icon={faTrashAlt}/></button>
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
                  {this.state.modalType=='insert'?
                    <button className="btn btn-success" onClick={()=>this.saveItem()}>
                    Add Item
                    </button>: <button className="btn btn-primary" onClick={()=>this.updateItem()}>
                    Update
                    </button>
  }
                    <button className="btn btn-danger" onClick={()=>this.modalInsert()}>Cancelar</button>
        </ModalFooter>

              
      </Modal>


      <Modal isOpen={this.state.deleteModal}>
            <ModalBody>
              Are you sure you want to delete the item {form && form.name}
            </ModalBody>
            <ModalFooter>
              <button className="btn btn-danger" onClick={()=>this.deleteItem()}>Yes</button>
              <button className="btn btn-secundary" onClick={()=>this.setState({deleteModal: false})}>No</button>
            </ModalFooter>
          </Modal>

          <button className="btn btn-success" onClick={()=>{this.setState({form: null, modalType: 'insert'}); this.modalInsert()}} >Add Item to do</button>
      </div>
    );
  }
}
export default App;
