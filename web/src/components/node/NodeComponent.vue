<template>
  <div>
    <!--width,height 画布的宽度，高度。 可以是百分比或像素，一般在dom元素上设置 -->

    <div id="network_id" class="network" style="height:60vh"></div>
    <div v-if="dialogVisible1" id="config1">
      <h2>配置节点：{{ node.id }} </h2>
      <form @submit.prevent="saveConfig">
        <label for="label" class="inline-elements" style="font-weight: bolder;">名称：</label>
        <input id="label" type="text" v-model="node.label" class="inline-elements">

        <label for="size" class="inline-elements">大小：</label>
        <input id="size" type="text" v-model="node.size" class="inline-elements">

        <label for="image" class="inline-elements">图片地址：</label>
        <input id="image" type="text" v-model="node.image" style="width: 700px;" class="inline-elements">

        <div class="form-check form-switch inline-elements">
          <label for="state">状态:{{ node.state === 1 ? "连接" : "断开连接" }}</label>
          <input v-if="node.state === 1" class="form-check-input" type="checkbox" role="switch" id="switch1"
            @click="update(node)" checked>
          <input v-else class="form-check-input" type="checkbox" role="switch" id="switch1" @click="update(node)">
        </div>

        <button type="button" class="btn btn-primary inline-elements" @click="update_node(node)">保存修改</button>







      </form>


    </div>

    <div v-if="dialogVisible2" id="config2">
      <h2>配置 </h2>
      <form @submit.prevent="saveConfig">
        <label for="from" class="inline-elements" style="font-weight: bolder;">起点：</label>
        <input id="from" type="text" v-model="edge.fromn" class="inline-elements">

        <label for="to" class="inline-elements">终点：</label>
        <input id="to" type="text" v-model="edge.ton" class="inline-elements">

        <label for="label1" class="inline-elements">标签：</label>
        <input id="label1" type="text" v-model="edge.label" style="width: 200px;" class="inline-elements">

        <button type="button" class="btn btn-primary inline-elements" @click="update_edge(edge)">保存修改</button>

      </form>


    </div>
    <div v-if="dialogVisible3" id="config3">
      <h2>增加节点 </h2>
      <form @submit.prevent="saveConfig">
        <label for="label" class="inline-elements" style="font-weight: bolder;">名称：</label>
        <input id="label" type="text" v-model="addnode.label" class="inline-elements">

        <label for="size" class="inline-elements">大小：</label>
        <input id="size" type="text" v-model="addnode.size" class="inline-elements">

        <label for="image" class="inline-elements">图片地址：</label>
        <input id="image" type="text" v-model="addnode.image" style="width: 700px;" class="inline-elements">

      </form>
      <button type="button" class="btn btn-primary inline-elements" @click="add_node(addnode)">提交</button>


    </div>
    <div v-if="dialogVisible4" id="config4">
      <h2>增加边 </h2>
      <form @submit.prevent="saveConfig">
        <label for="from" class="inline-elements" style="font-weight: bolder;">起点：</label>
        <input id="from" type="text" v-model="addedge.from" class="inline-elements">

        <label for="to" class="inline-elements">终点：</label>
        <input id="to" type="text" v-model="addedge.to" class="inline-elements">

        <label for="length" class="inline-elements">长度：</label>
        <input id="length" type="text" v-model="addedge.length" class="inline-elements">

        <label for="label1" class="inline-elements">标签：</label>
        <input id="label1" type="text" v-model="addedge.label" style="width: 200px;" class="inline-elements">

        <button type="button" class="btn btn-primary inline-elements" @click="add_edge(addedge)">提交</button>

      </form>


    </div>
    <div class="button-container">
      <button type="button" class="btn btn-primary" @click="add_node_button()">增加节点</button>
      <button type="button" class="btn btn-success" @click="add_edge_button()">增加边</button>
    </div>

    <el-dialog title="测试框" :visible="dialogVisible" width="width">


    </el-dialog>

  </div>
</template>
<script>
import $ from 'jquery'
import { useStore } from 'vuex'
import Vis from "vis";
export default {
  name: "NodeComponent",


  data() {
    const store = useStore();
    return {
      diyedge: [],
      addnode: {},
      addedge: {},
      dialogVisible1: false,
      dialogVisible2: false,
      dialogVisible3: false,
      dialogVisible4: false,
      selectedNodeId: -1,
      selectedEdgeId: -1,
      store,
      dialogVisible: false,
      node: {},
      edge: {},
      nodes: [],
      edges: [],
      // network:null,
      container: null,
      //   节点数组
      nodesArray: [
        // {
        //   id: 0,
        //   label: "大前端",
        //   color: { background: "yellow" }
        // },
        // {
        //   id: 1,
        //   label: "HTML",  
        //   color: { background: "pink" }
        // },
        // {
        //   id: 2,
        //   label: "JavaScript",
        //   color: { background: "pink" }
        // },
        // {
        //   id: 3,
        //   label: "CSS",
        //   color: { background: "pink" }
        // },
        // {
        //   id: 4,
        //   label: "三大主流框架",
        //   color: { background: "pink" }
        // },
        // {
        //   id: 5,
        //   label: "vue.js",
        //   color: { background: "pink" }
        // },
        // {
        //   id: 6,
        //   label: "react.js",
        //   color: { background: "pink" }
        // },
        // {
        //   id: 7,
        //   label: "angular.js",
        //   color: { background: "pink" }
        // }
      ],
      //   关系线数组
      edgesArray: [
        // { from: 0, to: 1, label: "ddd" },
        {

          from: 1, to: 2, label: "aaa", color: {
            color: "lightgreen",
            highlight: "lightgreen",
            hover: "lightblue",
            // inherit: "from",
            // opacity: 1.0
          },
          length: 300,
        },
        // { from: 0, to: 2, label: "step1" },
        // { from: 0, to: 3, label: "step1" },
        // { from: 0, to: 4, label: "step1" },
        // { from: 4, to: 5, label: "step2" },
        // { from: 4, to: 6, label: "step2" },
        // { from: 4, to: 7, label: "step2" }
      ],
      options: {},
      data: {}
    };
  },
  methods: {
    init() {
      let _this = this;

      //1.创建一个nodes数组
      _this.nodes = new Vis.DataSet(_this.nodesArray);
      //2.创建一个edges数组
      _this.edges = new Vis.DataSet(_this.edgesArray);
      _this.container = document.getElementById("network_id");
      _this.data = {
        nodes: _this.nodes,
        edges: _this.edges
      };
      _this.options = {
        autoResize: true, //网络将自动检测其容器的大小调整，并相应地重绘自身
        locale: "cn", //语言设置：工具栏显示中文
        //设置语言
        locales: {
          cn: {
            //工具栏中文翻译
            edit: "编辑",
            del: "删除当前节点或关系",
            back: "返回",
            addNode: "添加节点",
            addEdge: "添加连线",
            editNode: "编辑节点",
            editEdge: "编辑连线",
            addDescription: "点击空白处可添加节点",
            edgeDescription: "点击某个节点拖拽连线可连接另一个节点",
            editEdgeDescription: "可拖拽连线改变关系",
            createEdgeError: "无法将边连接到集群",
            deleteClusterError: "无法删除集群.",
            editClusterError: "无法编辑群集'"
          }
        },

        // 设置节点样式
        nodes: {
          shape: "circle",
          size: 50,
          font: {
            //字体配置
            size: 32
          },
          color: {
            // border: "#2B7CE9", //节点边框颜色
            background: "#97C2FC", //节点背景颜色
            highlight: {
              //节点选中时状态颜色
              border: "#2B7CE9",
              background: "#D2E5FF"
            },
            hover: {
              //节点鼠标滑过时状态颜色
              border: "#2B7CE9",
              background: "#D2E5FF"
            }
          },
          borderWidth: 0, //节点边框宽度，单位为px
          borderWidthSelected: 2 //节点被选中时边框的宽度，单位为px
        },
        // 边线配置
        edges: {
          width: 1,
          length: 260,
          // color: {
          //   color: "#848484",
          //   highlight: "#848484",
          //   hover: "#848484",
          //   inherit: "from",
          //   opacity: 1.0
          // },
          shadow: true,
          smooth: {
            //设置两个节点之前的连线的状态
            enabled: true //默认是true，设置为false之后，两个节点之前的连线始终为直线，不会出现贝塞尔曲线
          },
          arrows: { to: true } //箭头指向to
        },
        //计算节点之前斥力，进行自动排列的属性
        physics: {
          enabled: true, //默认是true，设置为false后，节点将不会自动改变，拖动谁谁动。不影响其他的节点
          barnesHut: {
            gravitationalConstant: -4000,
            centralGravity: 0.3,
            springLength: 120,
            springConstant: 0.04,
            damping: 0.09,
            avoidOverlap: 0
          }
        },
        //用于所有用户与网络的交互。处理鼠标和触摸事件以及导航按钮和弹出窗口
        interaction: {



          dragNodes: true, //是否能拖动节点
          dragView: true, //是否能拖动画布
          hover: true, //鼠标移过后加粗该节点和连接线
          multiselect: true, //按 ctrl 多选
          selectable: true, //是否可以点击选择
          selectConnectedEdges: true, //选择节点后是否显示连接线
          hoverConnectedEdges: true, //鼠标滑动节点后是否显示连接线
          zoomView: true //是否能缩放画布
        },
        //操作模块:包括 添加、删除、获取选中点、设置选中点、拖拽系列、点击等等
        manipulation: {
          enabled: true, //该属性表示可以编辑，出现编辑操作按钮
          addNode: true,
          addEdge: true,
          // editNode: undefined,
          editEdge: true,
          deleteNode: true,
          deleteEdge: true
        }
      };

      _this.network = new Vis.Network(
        _this.container,
        _this.data,
        _this.options
      );
    },

    resetAllNodes() {
      let _this = this;
      _this.nodes.clear();
      _this.edges.clear();
      _this.nodes.add(_this.nodesArray);
      _this.edges.add(_this.edgesArray);
      _this.data = {
        nodes: _this.nodes,
        edges: _this.edges
      };
      //   network是一种用于将包含点和线的网络和网络之间的可视化展示
      _this.network = new Vis.Network(
        _this.container,
        _this.data,
        _this.options
      );
    },
    resetAllNodesStabilize() {
      let _this = this;
      _this.resetAllNodes();
      _this.network.stabilize();
    },
    refresh_edges() {
      let _this = this;
      $.ajax({
        url: "http://127.0.0.1:3000/edge/getlist/",
        type: "get",
        headers: {
          Authorization: "Bearer " + this.store.state.user.token,
        },
        success: (resp) => {
          // 更新nodesArray的值


          let t = [];
          this.diyedge = t;

          for (let i = 0; i < resp.length; i++) {
            t[i] = {
              id: resp[i].id,
              from: resp[i].fromn,
              to: resp[i].ton,
              label: resp[i].label,
              color: {
                color: resp[i].color,
                highlight: resp[i].highlight,
                hover: resp[i].hover
              },
              length: resp[i].length
            };
          }
          _this.edgesArray = t;
          // 清空并更新Vis.DataSet实例

          _this.edges.clear();
          _this.edges.add(t); // 使用更新后的resp来更新nodes
          // 如果您有edges的数据，也进行类似的操作
          // _this.edges.add(yourUpdatedEdgesArray);

          // 重新设置_data对象并重新渲染图
          _this.data = {
            nodes: _this.nodes,
            edges: _this.edges
          };

          _this.network.setData(_this.data); // 使用setData方法重新设置数据
          _this.network.stabilize(); // 可选，如果您希望图稳定
        }
      });
    },

    refresh_nodes() {
      let _this = this;
      $.ajax({
        url: "http://127.0.0.1:3000/node/getlist/",
        type: "get",
        headers: {
          Authorization: "Bearer " + this.store.state.user.token,
        },
        success(resp) {
          // 更新nodesArray的值
          _this.nodesArray = resp;


          // 清空并更新Vis.DataSet实例
          _this.nodes.clear();
          // _this.edges.clear();
          _this.nodes.add(resp); // 使用更新后的resp来更新nodes
          // 如果您有edges的数据，也进行类似的操作
          // _this.edges.add(yourUpdatedEdgesArray);

          // 重新设置_data对象并重新渲染图
          _this.data = {
            nodes: _this.nodes,
            edges: _this.edges
          };

          _this.network.setData(_this.data); // 使用setData方法重新设置数据
          _this.network.stabilize(); // 可选，如果您希望图稳定
        }
      });
    },
    get_node(id) {
      $.ajax({
        url: "http://127.0.0.1:3000/node/getlist/",
        type: "get",
        headers: {
          Authorization: "Bearer " + this.store.state.user.token,
        },
        success: (resp) => {
          // 更新nodesArray的值
          for (let i = 0; i < resp.length; i++) {
            if (resp[i].id == id) {
              this.node = resp[i];
              break;
            }
          }


        }
      })
    },
    get_edge(id) {
      $.ajax({
        url: "http://127.0.0.1:3000/edge/getlist/",
        type: "get",
        headers: {
          Authorization: "Bearer " + this.store.state.user.token,
        },
        success: (resp) => {
          // 更新nodesArray的值
          for (let i = 0; i < resp.length; i++) {
            if (resp[i].id == id) {
              this.edge = resp[i];
              break;
            }
          }


        }
      })
    },
    update_node(node) {

      $.ajax({
        url: "http://127.0.0.1:3000/node/update/",
        type: "post",
        data: {
          id: node.id,
          label: node.label,
          size: node.size,
          shape: node.shape,
          image: node.image,
          state: node.state,
        },
        headers: {
          Authorization: "Bearer " + this.store.state.user.token,
        },
        success: (resp) => {
          this.refresh_nodes();
          if (resp.error_message === "success") {

            let id = node.id;

            if (node.state === 0) {
              for (let a of this.diyedge) {
                if (a.from === id || a.to === id) {
                  let edge = {
                    id: a.id,
                    fromn: a.from,
                    ton: a.to,
                    label: a.label,
                    color: "red",
                    highlight: "red",
                    hover: "lightblue",
                    length: a.length,
                  }

                  this.update_edge(edge);
                  this.refresh_edges();
                }
              }
            }
            else {
              console.log("节点", this.nodesArray);


              for (let a of this.diyedge) {
                if (a.from === id) {
                  for (let b of this.nodesArray) {
                    if (b.id === a.to && b.state === 1) {
                      let edge = {
                        id: a.id,
                        fromn: a.from,
                        ton: a.to,
                        label: a.label,
                        color: "lightgreen",
                        highlight: "lightgreen",
                        hover: "lightblue",
                        length: a.length,
                      }
                      console.log("连接成功", b.id);
                      this.update_edge(edge);
                      this.refresh_edges();
                      break;
                    }

                  }
                }
                else if (a.to === id) {
                  for (let b of this.nodesArray) {
                    if (b.id === a.from && b.state === 1) {
                      let edge = {
                        id: a.id,
                        fromn: a.from,
                        ton: a.to,
                        label: a.label,
                        color: "lightgreen",
                        highlight: "lightgreen",
                        hover: "lightblue",
                        length: a.length,
                      }

                      this.update_edge(edge);
                      this.refresh_edges();
                      break;
                    }


                  }
                }
              }






            }

            this.refresh_edges();
          }
        }
      })
    },
    update_edge(edge) {

      $.ajax({
        url: "http://127.0.0.1:3000/edge/update/",
        type: "post",
        data: {
          id: edge.id,
          from: edge.fromn,
          to: edge.ton,
          label: edge.label,
          color: edge.color,
          highlight: edge.color,
          hover: edge.hover,
          length: edge.length,
        },
        headers: {
          Authorization: "Bearer " + this.store.state.user.token,
        },
        success: (resp) => {

          if (resp.error_message === "success") {


            this.refresh_edges();
          }
        }
      })
    },
    update(node) {
      node.state ^= 1;

    },
    showAddEdgeConfigDialog() {
      // 基于点击的节点ID，您可以实现逻辑来显示一个模态框或配置栏
      // 例如，您可以设置一个data属性来控制模态框的显示与隐藏
      // 并传递nodeId给模态框组件，以便编辑相应的节点
      this.dialogVisible4 = true; // 假设这将显示一个模态框
      // 同时，您可以将点击的节点ID传递给模态框组件，让其编辑


    },
    disshowAddEdgeConfigDialog() {
      // 基于点击的节点ID，您可以实现逻辑来显示一个模态框或配置栏
      // 例如，您可以设置一个data属性来控制模态框的显示与隐藏
      // 并传递nodeId给模态框组件，以便编辑相应的节点
      this.dialogVisible4 = false; // 假设这将显示一个模态框
      // 同时，您可以将点击的节点ID传递给模态框组件，让其编辑


    },
    showAddNodeConfigDialog() {
      // 基于点击的节点ID，您可以实现逻辑来显示一个模态框或配置栏
      // 例如，您可以设置一个data属性来控制模态框的显示与隐藏
      // 并传递nodeId给模态框组件，以便编辑相应的节点
      this.dialogVisible3 = true; // 假设这将显示一个模态框
      // 同时，您可以将点击的节点ID传递给模态框组件，让其编辑


    },
    disshowAddNodeConfigDialog() {
      // 基于点击的节点ID，您可以实现逻辑来显示一个模态框或配置栏
      // 例如，您可以设置一个data属性来控制模态框的显示与隐藏
      // 并传递nodeId给模态框组件，以便编辑相应的节点
      this.dialogVisible3 = false; // 假设这将显示一个模态框
      // 同时，您可以将点击的节点ID传递给模态框组件，让其编辑


    },
    showEdgeConfigDialog(EdgeId) {
      // 基于点击的节点ID，您可以实现逻辑来显示一个模态框或配置栏
      // 例如，您可以设置一个data属性来控制模态框的显示与隐藏
      // 并传递nodeId给模态框组件，以便编辑相应的节点
      this.dialogVisible2 = true; // 假设这将显示一个模态框
      // 同时，您可以将点击的节点ID传递给模态框组件，让其编辑
      this.selectedEdgeId = EdgeId; // 假设您有一个data属性selectedNodeId来存储当前选择的节点ID

    },
    disshowEdgeConfigDialog() {
      // 基于点击的节点ID，您可以实现逻辑来显示一个模态框或配置栏
      // 例如，您可以设置一个data属性来控制模态框的显示与隐藏
      // 并传递nodeId给模态框组件，以便编辑相应的节点
      this.dialogVisible2 = false; // 假设这将显示一个模态框
      // 同时，您可以将点击的节点ID传递给模态框组件，让其编辑
      this.selectedEdgeId = null; // 假设您有一个data属性selectedNodeId来存储当前选择的节点ID

    },
    showNodeConfigDialog(nodeId) {
      // 基于点击的节点ID，您可以实现逻辑来显示一个模态框或配置栏
      // 例如，您可以设置一个data属性来控制模态框的显示与隐藏
      // 并传递nodeId给模态框组件，以便编辑相应的节点
      this.dialogVisible1 = true; // 假设这将显示一个模态框
      // 同时，您可以将点击的节点ID传递给模态框组件，让其编辑
      this.selectedNodeId = nodeId; // 假设您有一个data属性selectedNodeId来存储当前选择的节点ID

    },
    disshowNodeConfigDialog() {
      // 基于点击的节点ID，您可以实现逻辑来显示一个模态框或配置栏
      // 例如，您可以设置一个data属性来控制模态框的显示与隐藏
      // 并传递nodeId给模态框组件，以便编辑相应的节点
      this.dialogVisible1 = false; // 假设这将显示一个模态框
      // 同时，您可以将点击的节点ID传递给模态框组件，让其编辑
      this.selectedNodeId = null; // 假设您有一个data属性selectedNodeId来存储当前选择的节点ID

    },
    add_node_button() {
      this.disshowNodeConfigDialog();
      this.disshowEdgeConfigDialog();
      this.disshowAddEdgeConfigDialog();
      this.showAddNodeConfigDialog();
    },
    add_edge_button() {
      this.disshowNodeConfigDialog();
      this.disshowEdgeConfigDialog();
      this.showAddEdgeConfigDialog();
      this.disshowAddNodeConfigDialog();
    },

    add_node(addnode) {

      $.ajax({
        url: "http://127.0.0.1:3000/node/add/",
        type: "post",
        data: {
          label: addnode.label,
          size: addnode.size,
          shape: "image",
          image: addnode.image,
          state: 0,
        },
        headers: {
          Authorization: "Bearer " + this.store.state.user.token,
        },
        success: (resp) => {
          console.log(resp);
          this.refresh_nodes();
        }
      })
    },
    add_edge(addedge) {
      $.ajax({
        url: "http://127.0.0.1:3000/edge/add/",
        type: "post",
        data: {
          length: addedge.length,
          from: addedge.from,
          to: addedge.to,
          label: addedge.label,
          color: "lightgreen",
          hover: "lightblue",
          highlight: "lightgreen"

        },
        headers: {
          Authorization: "Bearer " + this.store.state.user.token,
        },
        success: (resp) => {
          console.log(resp);
          this.refresh_edges();
        }
      })
    }

  },

  mounted() {
    this.init();
    this.refresh_nodes();
    this.refresh_edges();
    // 点击事件
    this.network.on("click", params => {

      // this.network.addEdgeMode();
      if (params.nodes.length > 0) {
        const clickedNodeId = params.nodes[0]; // 获取点击的第一个节点的ID
        console.log("点击的节点ID:", clickedNodeId);
        this.get_node(clickedNodeId);
        this.disshowEdgeConfigDialog();
        this.showNodeConfigDialog(clickedNodeId);
        this.disshowAddEdgeConfigDialog();
        this.disshowAddNodeConfigDialog();

      }
      else if (params.edges.length > 0) {
        const clickedEdgeId = params.edges[0]; // 获取点击的第一个边的ID
        console.log("点击的边ID:", clickedEdgeId);
        // 在这里可以执行你想要的操作，比如获取边信息或执行其他操作
        this.get_edge(clickedEdgeId);
        this.disshowNodeConfigDialog();
        this.disshowAddEdgeConfigDialog();
        this.disshowAddNodeConfigDialog();

        this.showEdgeConfigDialog(clickedEdgeId);

      }
      else {
        this.disshowNodeConfigDialog();
        this.disshowEdgeConfigDialog();
        this.disshowAddEdgeConfigDialog();
        this.disshowAddNodeConfigDialog();
      }
    });
    // 点击鼠标右键事件
    this.network.on("oncontext", params => {
      console.log("右击", params);
      this.dialogVisible = true;
    });
  }
};
</script>
<style lang="less" scoped>
.form-switch .form-check-input:checked {
  background-color: green !important;
  /* 背景色为绿色 */
  border-color: green !important;
  /* 边框颜色为绿色 */
}

.form-switch .form-check-input:checked+.form-check-label {
  color: green !important;
  /* 文本颜色为绿色 */
}

.inline-elements {
  display: inline-block;
}




.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 30vh;
  /* 设置容器高度为视窗高度，可以根据需要调整 */

}

/* 可以根据需要调整底边距离 */


/* 当状态为开启时的样式 */
</style>