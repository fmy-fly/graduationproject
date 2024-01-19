/* eslint-disable */
<template>
    <div class="col-qw">
        <div class="card" style="margin-top: 20px">
            <div class="card-header">
                <span style="font-size: 130%">自定义策略</span>
                <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                    data-bs-target="#add-diydefence-btn">
                    创建策略
                </button>

                <!-- Modal -->
                <!-- Button trigger modal -->

                <div class="modal fade" id="add-diydefence-btn" tabindex="-1">
                    <div class="modal-dialog modal-xl">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">创建策略</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <div class="mb-3">
                                    <label for="add-diydefence-title" class="form-label">名称</label>
                                    <input v-model="diydefenceadd.title" type="text" class="form-control"
                                        id="add-diydefence-title" placeholder="请输入策略名称">
                                </div>
                                <div class="mb-3">
                                    <label for="add-diydefence-description" class="form-label">简介</label>
                                    <textarea v-model="diydefenceadd.description" class="form-control"
                                        id="add-diydefence-description" rows="3" placeholder="请输入策略简介"></textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="add-diydefence-code" class="form-label">代码</label>
                                    <VAceEditor v-model:value="diydefenceadd.content" @init="editorInit" lang="c_cpp"
                                        theme="textmate" style="height: 300px" />
                                </div>
                            </div>
                            <div class="modal-footer">
                                <div class="error-message">{{ diydefenceadd.error_message }}</div>
                                <button type="button" class="btn btn-primary" @click="add_diydefence()">保存修改</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                            </div>
                        </div>
                    </div>
                </div>










            </div>











            <div class="card-body">
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>名称</th>
                            <th>简介</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="diydefence in diydefences" :key="diydefence.id">
                            <td>{{ diydefence.title }}</td>
                            <td>{{ diydefence.description }}</td>
                            <td>{{ diydefence.createtime }}</td>
                            <td>{{ diydefence.modifytime }}</td>
                            <td>
                                <button type="button" class="btn btn-secondary" style="margin-right: 10px"
                                    data-bs-toggle="modal"
                                    :data-bs-target="'#update-diydefence-modal-' + diydefence.id">修改</button>
                                <button type="button" class="btn btn-danger"
                                    @click="remove_diydefence(diydefence)">删除</button>

                                <div class="modal fade" :id="'update-diydefence-modal-' + diydefence.id" tabindex="-1">
                                    <div class="modal-dialog modal-xl">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title">创建策略</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="mb-3">
                                                    <label for="add-diydefence-title" class="form-label">名称</label>
                                                    <input v-model="diydefence.title" type="text" class="form-control"
                                                        id="add-diydefence-title" placeholder="请输入策略名称">
                                                </div>
                                                <div class="mb-3">
                                                    <label for="add-diydefence-description" class="form-label">简介</label>
                                                    <textarea v-model="diydefence.description" class="form-control"
                                                        id="add-diydefence-description" rows="3"
                                                        placeholder="请输入策略简介"></textarea>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="add-diydefence-code" class="form-label">代码</label>
                                                    <VAceEditor v-model:value="diydefence.content" @init="editorInit"
                                                        lang="c_cpp" theme="textmate" style="height: 300px" />
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <div class="error-message">{{ diydefence.error_message }}</div>
                                                <button type="button" class="btn btn-primary"
                                                    @click="update_diydefence(diydefence)">保存修改</button>
                                                <button type="button" class="btn btn-secondary"
                                                    data-bs-dismiss="modal">取消</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, reactive } from 'vue'
import $ from 'jquery'
import { useStore } from 'vuex'

import ace from 'ace-builds'

import { Modal } from 'bootstrap/dist/js/bootstrap'
import { VAceEditor } from 'vue3-ace-editor'

export default {
    name: "DiyDefenceComponent",
    components: {
        VAceEditor
    },
    setup() {



        // 如果您有更多的开关和标签，可以继续按照这种模式进行扩展


        ace.config.set(
            "basePath",
            "https://cdn.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/")





        const store = useStore()
        let diydefences = ref([])

        const diydefenceadd = reactive({
            title: "",
            description: "",
            content: "",
            error_message: "",
        })

        const refresh_diydefences = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/defence/diydefence/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    diydefences.value = resp
                }
            })
        }


        refresh_diydefences()

        const add_diydefence = () => {
            diydefenceadd.error_message = ""
            $.ajax({
                url: "http://127.0.0.1:3000/defence/diydefence/add/",
                type: "post",
                data: {
                    title: diydefenceadd.title,
                    description: diydefenceadd.description,
                    content: diydefenceadd.content,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        diydefenceadd.title = ""
                        diydefenceadd.description = ""
                        diydefenceadd.content = ""
                        Modal.getInstance("#add-diydefence-btn").hide()
                        refresh_diydefences()
                    } else {
                        diydefenceadd.error_message = resp.error_message
                    }
                }
            })
        }




        const update_diydefence = (diydefence) => {
            diydefenceadd.error_message = ""

            $.ajax({
                url: "http://127.0.0.1:3000/defence/diydefence/update/",
                type: "post",
                data: {
                    id: diydefence.id,
                    title: diydefence.title,
                    description: diydefence.description,
                    content: diydefence.content,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(diydefence)

                    if (resp.error_message === "success") {
                        Modal.getInstance('#update-diydefence-modal-' + diydefence.id).hide()
                        refresh_diydefences()
                    } else {
                        diydefenceadd.error_message = resp.error_message
                    }
                }
            })
        }



        const remove_diydefence = (diydefence) => {
            $.ajax({
                url: "http://127.0.0.1:3000/defence/diydefence/remove/",
                type: "post",
                data: {
                    id: diydefence.id,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        refresh_diydefences()
                    }
                }
            })
        }


        return {
            diydefences,
            diydefenceadd,
            add_diydefence,
            update_diydefence,
            remove_diydefence,
            refresh_diydefences









        }
    }
}
</script>

<style scoped>
#b-title {
    font: bolder
}

#c-title {
    color: blue
}

/* 自定义样式将开关颜色设置为绿色 */
.form-switch .form-check-input:checked {

    background-color: green !important;
    /* 背景色为绿色 */
    border-color: green !important;
    /* 边框颜色为绿色 */
}

.form-switch .form-check-input:checked+.form-check-label {
    color: green !important
        /* 文本颜色为绿色 */
}

div.error-message {
    color: red
}
</style>