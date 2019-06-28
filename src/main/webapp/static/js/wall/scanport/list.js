var vm = new Vue({
    el : '#dpLTE',
    data : {
    },
    methods: {
        change:function () {
        },
        craw:function () {
            vm.crawlPath = $("#crawlPath").val();
            vm.savePath = $("#savePath").val();
            var ck = vm.checkList.toString();
            if(vm.crawlPath.length < 8){
                $("#savePath").focus();
                layer.msg("请正确填写爬取地址！");
            }else if(vm.savePath.length < 1){
                $("#savePath").focus();
                layer.msg("请正确填写存储地址！");
            }else if(ck.length < 1){
                layer.msg("请选择拉取项！");
            }else{
                vm.checkList = [];
                $('input[name="ck"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数
                    vm.checkList.push($(this).val());//将选中的值添加到数组chk_value中
                });
                $("#outPut").html("<hr/><br/>数据爬取中，请稍后...<br/>");
                $.ajax({
                    method: 'post',
                    url: 'list',
                    data: {
                        crawlPath: vm.crawlPath,
                        savelPath: vm.savePath,
                        ck: ck,
                    },
                    success:function(data){
                        var str = "<hr/><br/>数据爬取中，请稍后...<br/><hr/>";
                        for (i = 0; i < data.length; i++) {
                            str += data[i] + "<br>";
                            $("#outPut").html(str);
                                i++;
                        }
                    },
                    error:function(e){
                        console.log("数据异常："+e)
                    }
                })
            }
        }

    }
});