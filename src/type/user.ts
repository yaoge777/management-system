interface pageType {
  PageSize: number,
  PageNumber:number
}

export interface userType {
    id:number | null,
    username:string,
    password:string,
    email:string,
    phone:string,
    status:string,
    avatar:string | null,
    roleList:number[]
}

export interface searchType {
  username:string,
  email:string,
  phone:string
}

interface rolesType {
  roleId:string,
  roleName:string,
  roleDesc:string
}

export class userData{
  pageData: pageType = {
    PageSize: 5,
    PageNumber: 1
  };

  total =  0;

  tableList: userType[] = [];
  userForm: userType =  {
    id:null,
    username:"",
    password:"",
    email:"",
    phone:"",
    status:"",
    avatar:"",
    roleList:[]
  };
  
  searchForm: searchType = {
    username:'',
    email:'',
    phone:''
  };

  roles: rolesType[]=[]

  tableData:userType[] = [];

  modelType =  "update";

}