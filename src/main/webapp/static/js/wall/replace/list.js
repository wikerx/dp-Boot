var vm = new Vue({
    el : '#dpLTE',
    data : {
        crawlPath:null,
        savePath:null,
        crawlAccount:null,
        saveAccount:null,
        crawlPwd:null,
        savePwd:null
    },
    methods: {
        craw:function () {
            vm.crawlPath = $("#crawlPath").val();
            vm.savePath = $("#savePath").val();
            vm.crawlAccount = $("#crawlAccount").val();
            vm.saveAccount = $("#saveAccount").val();
            vm.crawlPwd = $("#crawlPwd").val();
            vm.savePwd = $("#savePwd").val();
            if(vm.crawlPath.length < 8){
                $("#savePath").focus();
                layer.msg("请正确填写源码地址！");
            }else if(vm.savePath.length < 1){
                $("#savePath").focus();
                layer.msg("请正确填写换机地址！");
            }else{
                $("#outPut").html("<hr/><br/>数据换机中，请稍后...<br/>");
                $.ajax({
                    method: 'post',
                    url: 'list',
                    data: {
                        crawlPath: vm.crawlPath,
                        savelPath: vm.savePath,
                        crawlAccount:vm.crawlAccount,
                        saveAccount:vm.saveAccount,
                        crawlPwd:vm.crawlPwd,
                        savePwd:vm.savePwd
                    },
                    success:function(data){
                        $("#outPut").html("<hr/><br/>数据换机中，请稍后...<br/><hr/><br/><strong style='color: green;font-size: 18px;'>换机成功，重启服务器生效</strong><br/>");
                    },
                    error:function(e){
                        $("#outPut").html("<hr/><br/>数据换机中，请稍后...<br/><hr/><br/><strong style='color: red;font-size: 18px;'>换机失败</strong><br/>");
                    }
                })
            }
        }

    }
});