interface menuType {
  menuId: number,
  component: string,
  path: string,
  redirect: string,
  name: string,
  title: string,
  icon: string,
  parentId: number,
  isLeaf: string,
  hidden: boolean,
}

export interface roleType {
  roleId: number,
  roleName: string,
  roleDesc: string,
  menuIdList: number[]
}

export interface pageType {
  PageSize: number,
  PageNumber:number
}

export class roleData{

  menuList: menuType[] = [];
  searchModel = {
    PageSize: 5,
    PageNumber: 1
  };

  roleForm: roleType = {
    roleId:0,
    roleName:"",
    roleDesc:"",
    menuIdList:[]
  };

  searchForm = {
    roleName:'',
  };

  tableList: roleType[] = [];

  total = 0;

  pageData: pageType = {
    PageSize: 5,
    PageNumber: 1
  };

  modelType =  "update";

}