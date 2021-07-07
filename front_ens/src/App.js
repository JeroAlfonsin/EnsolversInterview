import logo from './assets/images/logo.svg';
import './assets/css/App.css';
import React, {Component} from 'react';
import {ItemService} from './services/ItemService';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import {Panel} from 'primereact/panel';
import {Menubar} from 'primereact/menubar';
import {Dialog} from 'primereact/dialog';
import {InputText} from 'primereact/inputtext';
import {Button} from 'primereact/button';

import "primereact/resources/themes/nova/theme.css"
import "primereact/resources/primereact.min.css"
import "primeicons/primeicons.css"
export default class App extends Component{
  constructor(){
    super();
    this.state= {
      visible:false,
      item:{
        id:null,
        name:null
      }
    };
    this.items = [
      {
        label: 'New',
        icon: 'pi pi-fw pi-plus',
        command: () => {this.showSaveDialog()}
      },
      {
        label: 'Edit',
        icon: 'pi pi-fw pi-pencil',
        command: () => {alert('Edited')}
      },
      {
        label: 'Delete',
        icon: 'pi pi-fw pi-trash',
        command: () => {alert('Deleted')}
      }
    ];
    this.footer = (
      <div >
        <Button label="Create" icon='pi pi-check' onClick={this.save()}  />
      </div>
    );
    this.itemService= new ItemService();
    this.save=this.save.bind(this);
  }

    save()
    {
      this.itemService.saveItem(this.state.item).then(data => {
      console.log(data);
     });
    }


  componentDidMount()
  {
    this.itemService.getAll().then(data =>this.setState({items : data}))
  }



  render(){
    return (
      <div style={{width:'80%', margin: '0 auto', marginTop: '20px'}}>
        <Menubar model={this.items}/>
        <br/>
          <Panel header="Items to do">
            <DataTable value={this.state.items}>
              <Column field="id" header="ID"></Column>
              <Column field="name" header="Name"></Column>
            </DataTable>
          </Panel>
          <Dialog header="Create Item" visible={this.state.visible} style={{width:'500px'}} footer={this.footer} modal={true} onHide={()=>this.setState({visible:false})}>
              <span className="p-float-label">
              <InputText value={this.state.item.name} style={{width: '100%'}} onChange={(e) => {
                  let val=e.target.value;
                  this.setState(prevState =>{
                  let item = Object.assign({}, prevState.item);
                  item.name=e.val;
                  return {item};
              })}
            }/>
              <label htmlFor="name">Username</label>
              </span>
          </Dialog>
      </div>
    )
  }

  showSaveDialog()
  {
    this.setState({
      visible :true
    })
  }
}
