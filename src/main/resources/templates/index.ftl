<!DOCTYPE html>
<html lang="ko">
  <head>
    <title>스프링 배치 튜토리얼</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link
        rel="stylesheet"
        href="//cdn.jsdelivr.net/npm/element-plus/dist/index.css"
    />
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="https://unpkg.com/vue@next"></script>
    <script src="//unpkg.com/element-plus"></script>
    <style>
      table tr.job-row td {
        background-color: darkseagreen;
      }
      table tr.step-row td {
        background-color: cornsilk;
        padding-left: 10px;
      }
    </style>
  </head>
  <body>
    <div id="root">
      <div class="layout">
        <el-container>
          <el-header>스프링 배치 튜토리얼</el-header>
          <el-main>
            <el-row class="row-bg" justify="end">
              <el-button type="primary" @click="onLaunchJobClick()">Job 실행하기</el-button>
            </el-row>
            <el-row>
              <table>
                <colgorup>
                  <col style="width: 100px;">
                  <col style="width: calc(100% - 200px);">
                  <col style="width: 100px;">
                </colgorup>
                <thead>
                  <tr>
                    <th>type</th>
                    <th>message</th>
                    <th>operation</th>
                  </tr>
                </thead>
                <tbody v-if="log.length === 0">
                  <tr>
                    <td colspan="3">데이터가 없습니다</td>
                  </tr>
                </tbody>
                <tbody v-else>
                  <tr v-for="(data,index) in log" :class="{'job-row': data.type === 'JOB', 'step-row': data.type === 'STEP' }">
                    <td>{{data.type}}</td>
                    <td>{{data.message}}</td>
                    <td>
                      <el-button v-if="typeof data.payload === 'object' && data.payload !== null"
                          type="primary" size="small" @click="onDetailPayload(index)"
                      >Payload</el-button>
                    </td>
                  </tr>
                </tbody>

              </table>
            </el-row>
          </el-main>
        </el-container>
      </div>
    </div>
    <script>
      const app = Vue.createApp({
        mounted() {
          this.initSse();
        },
        data() {
          return {
            log: [],
          };
        },
        methods: {
          initSse() {
            const sse = new EventSource("http://localhost:8080/connect");

            sse.addEventListener('connect', (e) => {
              const { data } = e;
              console.log('connect event data: ',data);  // "connected!"
            });

            sse.addEventListener('message', e => {
              const { data } = e;
              const obj = JSON.parse(data);
              console.log(obj);
              this.log.push(obj);
            });
          },


          onLaunchJobClick() {
            axios.post('http://localhost:8080/api/batch/run')
              .then(response => {
                this.$message.success(response.data);
              })
              .catch(err => {
                console.error(err);
                this.$message.error('error');
              });
          },
          onDetailPayload(index) {
            this.$alert({
              type: 'info',
              message: JSON.stringify(this.log[index]?.payload),
            });
          }
        }
      });

      app.use(ElementPlus);
      app.mount('#root');
    </script>
  </body>
</html>