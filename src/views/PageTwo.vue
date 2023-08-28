<template>
  <div>
     <!-- dialogy -->
     <el-dialog
    v-model="dialogVisible"
    title="Tips"
    width="30%"
    :before-close="handleClose"
    >
      <el-form
        label-width="80px"
        :model="data.roleForm"
        style="max-width: 460px"
        :rules="rules"
        :inline="true"
        ref = "form"
      >
        <el-form-item label='用户名' prop='username'>
          <el-input v-model='data.roleForm.roleName' placeholder='请输入用户名'></el-input>
        </el-form-item>
        <el-form-item label='描述' prop='desc'>
          <el-input v-model='data.roleForm.roleDesc' placeholder='请输入desc'></el-input>
        </el-form-item>
        <el-form-item label='auth set' prop='menuIdList'>
           <el-tree ref="menuRef" style="width:85%" :data="data.menuList" :props="menuProps" show-checkbox default-expand-all node-key="menuId"></el-tree>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="Cancel">Cancel</el-button>
          <el-button type="primary" @click="Submit">
            Confirm
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!-- header button -->
    <div>
      <el-button @click='handleAdd' type='primary'>
        +新增
      </el-button>
      <el-form :inline='true' :model='data.searchForm'>
        <el-form-item>
          <el-input placeholder='请输入名称' v-model='data.searchForm.roleName'></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type='primary' @click="handleQuery">查询</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- main table -->
    <el-table
    :data="data.tableList"
    style="width: 100%">
    <el-table-column
      prop="roleName"
      label="角色名"
      width="180">
    </el-table-column>
    <el-table-column
      prop="roleDesc"
      label="描述"
      width="320">
    </el-table-column>

    <el-table-column label="操作" fixed="right">
      <template #default="scope">
        <el-button
          size="small"
          @click.prevent="handleEdit(scope.row)">编辑</el-button>
        <el-button
          size="small"
          type="danger"
          @click.prevent="handleDelete(scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

    <!-- pagination -->
    <div class="pagination-block">
      <el-pagination
        v-model:current-page="data.pageData.PageNumber"
        v-model:page-size="data.pageData.PageSize"
        :page-sizes="[5, 10, 15, 20]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="data.total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from "vue";
import {roleData, roleType} from "@/type/role"
import {getAllRoles, addRole, searchRole, deleteRole, updateRole} from "@/api/RoleManage"
import { ElMessageBox } from "element-plus";
import {getSelectMenu} from "@/api/RoleMenuManage"
import {getAllMenu} from "@/api/MenuManage"
let dialogVisible = ref(false)
const data = reactive(new roleData())
const menuRef = ref()
const rules = {
  role_name:[
    {required: true, message:'请输入用户名' },
  ],
  role_desc:[
    {required: true, message:"please input desc"}
  ]
}

const menuProps = {
  children:'children',
  label:'title'
}

const getRoles = () => {
  getAllRoles(data.searchModel).then(res => {
    data.tableList = res.data.data.data
    data.total = res.data.data.total
  })
}

const getMenu = () => {
  getAllMenu().then(res => {
    data.menuList = res.data.data;
  })
}

const handleClose = () => {
  dialogVisible.value = false
  data.roleForm = {
    roleId:0,
    roleName:"",
    roleDesc:"",
    menuIdList:[]
  }
  menuRef.value.setCheckedKeys([])
}

const Cancel = () => {
  ElMessageBox.alert("sure to cancel?", "reminder", {
    confirmButtonText: 'OK',
    cancelButtonText: 'No',
    type:'warning'
  }).then(() => {
    handleClose()
    ElMessageBox({
      type:'info',
      message:'already canceled'    
    })
  })
}

const Submit = () => {
  let keys = menuRef.value.getCheckedKeys()
  let halfkeys = menuRef.value.getHalfCheckedKeys()
  data.roleForm.menuIdList = keys.concat(halfkeys)
  if(data.modelType == "update"){
    updateRole(data.roleForm).then(() => {          
      getRoles()
      ElMessageBox({
        type:'info',
        message:'successfully updated'
      })
    })
  }else{
    addRole(data.roleForm).then(() => {
      getRoles()
      ElMessageBox({
        type:'info',
        message:'successfully added'
      })
    })
  }
  handleClose()
}

const handleAdd = () => {
  data.modelType="add"
  dialogVisible.value = true
}

const handleQuery = () => {
  if(Object.values(data.searchForm).some(v => !!v)){
    searchRole(data.searchForm, data.pageData).then(res => {
    data.tableList = res.data.data.data
    data.total = res.data.data.total
    data.searchForm = {
      roleName:''
    }
  })
  } else {
    ElMessageBox({
      type:'warning',
      message:'please at least input one condition'    
    })
  }
}

const handleDelete = (data:roleType) => {
  console.log("deleting")
  deleteRole(data).then(() => {
    getRoles()
  })
    
}

const handleEdit = (para: roleType) => {
  data.modelType = 'update'
  data.roleForm = JSON.parse(JSON.stringify(para))
  getSelectMenu(data.roleForm).then(res => {
    for(let d=0; d<res.data.data.length; d++){
      menuRef.value.setChecked(res.data.data[d].menuId,true,false)
    }
    })
  dialogVisible.value = true
}

const handleSizeChange = (ps:number) => {
  data.pageData.PageSize = ps
  getRoles()
}

const handleCurrentChange = (cp:number) => {
  data.pageData.PageNumber = cp
  getRoles()
}

onMounted(() => {
  getRoles()
  getMenu()
})
</script>

<style scoped>

</style>