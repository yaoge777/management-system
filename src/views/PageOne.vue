<template>
  <div>
    <!-- dislogy -->
    <el-dialog
    v-model="dialogVisible"
    title="Tips"
    width="30%"
    :before-close="handleClose"
    >
      <el-form
        label-width="80px"
        :model="data.userForm"
        style="max-width: 460px"
        :rules="rules"
        :inline="true"
        ref = "form"
      >
        <el-form-item label='用户名' prop='username'>
          <el-input v-model='data.userForm.username' placeholder='请输入用户名'></el-input>
        </el-form-item>
        <el-form-item label='密码' prop='password'>
          <el-input v-model='data.userForm.password' placeholder='请输入密码'></el-input>
        </el-form-item>
        <el-form-item label='邮箱' prop='email'>
          <el-input v-model='data.userForm.email' placeholder='请输入邮箱'></el-input>
        </el-form-item>
        <el-form-item label='电话' prop='phone'>
          <el-input v-model='data.userForm.phone' placeholder='请输入电话'></el-input>
        </el-form-item>
        <el-form-item label='状态' prop='status'>
          <el-input v-model='data.userForm.status' placeholder='请输入状态'></el-input>
        </el-form-item>
        <el-checkbox-group 
          v-model="data.userForm.roleList"
          :max="2">
          <el-checkbox v-for="role in data.roles" :label="role.roleId" :key="role.roleId">{{role.roleName}}</el-checkbox>
        </el-checkbox-group>
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
          <el-input placeholder='请输入名称' v-model='data.searchForm.username'></el-input>
        </el-form-item>
        <el-form-item>
          <el-input placeholder='请输入email' v-model='data.searchForm.email'></el-input>
        </el-form-item>
        <el-form-item>
          <el-input placeholder='请输入phone' v-model='data.searchForm.phone'></el-input>
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
      prop="username"
      label="用户名"
      width="180">
    </el-table-column>
    <el-table-column
      prop="email"
      label="邮箱"
      width="320">
    </el-table-column>
    <el-table-column
      prop="phone"
      label="电话"
      width="320">
    </el-table-column>
    <el-table-column
      prop="status"
      label="状态"
      width="60">
    </el-table-column>

    <el-table-column
      label="角色"
      width="60">
      <template #default="scope">
        <span >{{ scope.row.roleList.length == 0 ? 'no' : data.roles.filter(v => v.roleId == scope.row.roleList[0])[0].roleName }}</span>
      </template>
    </el-table-column>

    <el-table-column label="操作" fixed="right">
      <template #default="scope">
        <el-button
          size="small"
          @click.prevent="handleEdit(scope.row.id)">编辑</el-button>
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
import { onMounted, reactive, ref} from 'vue';
import {getAllUsers,addUser, getUserById,updateUser,deleteUser,searchUser} from '@/api/UserManage'
import {userData, userType } from '@/type/user'
import { ElMessageBox } from 'element-plus';
import type { FormInstance} from 'element-plus'
import {getRoleList} from '@/api/RoleManage'
let dialogVisible = ref(false)
const rules = {
    username: [
      {required: true, message:'请输入用户名' },
    ],
    password: [
      {required: true, message:'请输入pw' },
    ],
    email:[
      {required: true, message:"please input email or phone"}
    ],
    phone:[
      {required: true, message:"please input email or phone"}
    ],
    status:[
      {required: true, message:"please input status"}
    ]
  }

const data = reactive(new userData())
const form = ref<FormInstance>()
const getUsers = () => {
  getAllUsers(data.pageData).then(res => {

    data.tableList = res.data.data.data


    data.total = res.data.data.total

    
  })
}

// close form
const handleClose = () => {
  data.userForm =  {
    id:null,
    username:"",
    password:"",
    email:"",
    phone:"",
    status:"",
    avatar:"",
    roleList:[]
  };
  dialogVisible.value = false
}

// cancel editing
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


//submit editing
const Submit = () => {
  if(data.modelType == 'update') {
    updateUser(data.userForm).then(() => {
      getUsers()
      ElMessageBox({
      type:'info',
      message:'already updated'    
    })
    })
  } else if (data.modelType == 'add') {
    addUser(data.userForm).then(() => {
      getUsers();
      ElMessageBox({
      type:'info',
      message:'successfully added'    
    })
    })
  }
  handleClose()
}

const handleAdd = () => {
  data.modelType = 'add'
  dialogVisible.value = true
}

const handleQuery = () => {
    if(Object.values(data.searchForm).some(v => !!v)){
    searchUser({...data.searchForm,...data.pageData}).then(res => {
      
      data.tableList = res.data.data.data
      data.total = res.data.data.total
      console.log(data.total)
      data.searchForm = {
        username:'',
        email:'',
        phone:''
      }
    })
  } else {
    ElMessageBox({
      type:'warning',
      message:'please at least input one condition'    
    })
  }
}

const handleSizeChange = (ps:number) => {
  data.pageData.PageSize = ps
  getUsers()
}

const handleCurrentChange = (cp:number) => {
  data.pageData.PageNumber = cp
  getUsers()
}

const handleEdit = (id:string) => {
  data.modelType = 'update'
  getUserById(id).then(res => {
    data.userForm = res.data.data.data
  })
  dialogVisible.value = true
}

const handleDelete = (form:userType) => {
  ElMessageBox.alert("sure to delete?", "reminder", {
    confirmButtonText: 'OK',
    cancelButtonText: 'No',
    type:'warning'
  }).then(() => {
    deleteUser(form).then(() => {
      ElMessageBox({
        type:'info',
        message:'already deleted'    
      })
      getUsers()
    }) 
  })
}

onMounted(() => {
  getRoleList().then(res => {

    data.roles = res.data.data.data


  })
  getUsers()
  
})
 




</script>

<style scoped>

</style>