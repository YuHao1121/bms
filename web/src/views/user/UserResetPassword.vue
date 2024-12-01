<!-- <script>
import { ElMessage } from "element-plus";
import { reactive } from 'vue'


// 定义数据模型
const updateData = reactive({
    oldPwd: '',
    newPwd: '',
    rePwd: ''
});

//调用后台接口完成更新
import { UserPasswordUpdateService } from "@/api/user.js";

//校验密码的函数
const validatePass2=(rule,value,callback)=>{
    if(value===''){
        callback(new Error('请再次确认新密码！'));
    }else if(value !== updateData.value.newPwd){//一定要先去获取响应式对象，不然你获取的是空密码，依然会提示错误信息
        callback(new Error('请确保两次输入的密码一样！'));
    }else{
        callback();
    }
}


//定义表单检验规则
const rules={
    oldPwd:[
        // required:true 必须传
        // trigger:'blur' 事件触发器，它在元素失去焦点（即用户离开该元素）时触发
        //min:5,max:16 5-16位非空字符
        {required:true,message:'请输入旧密码',trigger:'blur'}
    ],
    newPwd:[
        {required:true,message:'请输入新密码',trigger:'blur'},
        {min:5,max:16,message:'长度为5-16位非空字符',trigger:'blur'}
    ],
    rePwd:[
        {validator: validatePass2, trigger: 'blur'}
    ]
}

// import useUserInfoStore from '@/stores/userinfo.js';
// const userInfoStore = useUserInfoStore();
// const userInfo = ref({...userInfoStore.info})
const handleSubmit = async () => {
    try {
        let result = await UserPasswordUpdateService(updateData);
        ElMessage.success(result.msg ? result.msg : '更新成功');
    } catch (error) {
        ElMessage.error(error.response?.data?.msg || '更新失败');
    }
};

export default {
    setup() {
        return {
            updateData,
            rules,
            handleSubmit
        };
    }
};


</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>更新密码</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="updateData" :rules="rules" ref="formRef" label-width="100px" size="large">

                    <el-form-item  label="原密码" prop="oldPwd">
                        <el-input v-model="updateData.oldPwd" type="password"  placeholder="请输入原密码"></el-input>
                    </el-form-item>
  
                    <el-form-item label="新密码" prop="newPwd">
                        <el-input v-model="updateData.newPwd" type="password"  placeholder="请输入新密码"></el-input>
                    </el-form-item>
  
                    <el-form-item label="确认新密码" prop="rePwd">
                        <el-input v-model="updateData.rePwd" type="password"   placeholder="请再次输入新密码"></el-input>
                    </el-form-item>
  
                    <el-form-item>
                        <el-button type="primary" @click="handleSubmit">更新密码</el-button>
                    </el-form-item>

                    

                </el-form>
            </el-col>
        </el-row>
    
    </el-card>
  </template> -->
  

  

  
  <script>
import { ElMessage } from "element-plus";
import { reactive } from 'vue';
import { UserPasswordUpdateService } from "@/api/user.js";

// 定义数据模型
const updateData = reactive({
    oldPwd: '',
    newPwd: '',
    rePwd: ''
});

// 校验密码的函数
const validatePass2 = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次确认新密码！'));
    } else if (value !== updateData.newPwd) {
        callback(new Error('请确保两次输入的密码一样！'));
    } else {
        callback();
    }
};

// 定义表单检验规则
// required:true 必须传
// trigger:'blur' 事件触发器，它在元素失去焦点（即用户离开该元素）时触发
//min:5,max:16 5-16位非空字符
const rules = {
    oldPwd: [{ required: true, message: '请输入旧密码', trigger: 'blur' }],
    newPwd: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5-16位非空字符', trigger: 'blur' }
    ],
    rePwd: [{ validator: validatePass2, trigger: 'blur' }]
};

const handleSubmit = async () => {
    try {
        console.log('updateData:', updateData);
        let result = await UserPasswordUpdateService(updateData);
        ElMessage.success(result.msg ? result.msg : '更新成功');
    } catch (error) {
        ElMessage.error(error.response?.data?.msg || '更新失败');
    }
};

export default {
    setup() {
        return {
            updateData,
            rules,
            handleSubmit
        };
    }
};
</script>

<template>
<el-card class="page-container">
    <template #header>
        <div class="header">
            <span>更新密码</span>
        </div>
    </template>
    <el-row>
        <el-col :span="12">
            <el-form :model="updateData" :rules="rules" ref="formRef" label-width="100px" size="large">
                <el-form-item label="原密码" prop="oldPwd">
                    <el-input v-model="updateData.oldPwd" type="password" placeholder="请输入原密码"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="newPwd">
                    <el-input v-model="updateData.newPwd" type="password" placeholder="请输入新密码"></el-input>
                </el-form-item>
                <el-form-item label="确认新密码" prop="rePwd">
                    <el-input v-model="updateData.rePwd" type="password" placeholder="请再次输入新密码"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="handleSubmit">更新密码</el-button>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</el-card>
</template>
