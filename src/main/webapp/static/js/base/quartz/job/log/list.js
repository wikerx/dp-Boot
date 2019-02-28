/**
 * 定时任务日志; InnoDB free: 27648 kBjs
 */

$(function () {
	initialPage();
	getGrid();
});

function initialPage() {
	$(window).resize(function() {
		$('#dataGrid').bootstrapTable('resetView', {height: $(window).height()-56});
	});
}

function getGrid() {
	$('#dataGrid').bootstrapTableEx({
		url: '../../quartz/job/log/list?_' + $.now(),
		height: $(window).height()-56,
		queryParams: function(params){
			params.jobId = vm.jobId;
			params.beanName = vm.beanName;
			params.methodName = vm.methodName;
			params.status = vm.status;
			return params;
		},
		columns: [
			{checkbox: true},
            {field : "logId", title : "序号", width : "5%"},
            {field : "jobId", title : "任务id", width : "5%"},
            {field : "beanName", title : "spring bean名称", width : "10%"},
            {field : "methodName", title : "方法名", width : "10%"},
            {field : "params", title : "参数", width : "20%"},
            {field : "status", title : "任务状态", width : "5%",
                formatter : function(value , row, index) {
                    if (value === 0) {
                        return '<b style="color: #FF3300;">执行失败</b>'
                    } else if (value === 1) {
                        return '<b style="color: #00B83F;">执行成功</b>'
                    } else {
                        return '<b style="color: #9d9d9d;">未知状态</b>'
                    }
                }
            },
            {field : "error", title : "失败信息", width : "20%"},
            {field : "times", title : "耗时(单位：毫秒)", width : "10%"},
            {field : "gmtCreate", title : "创建时间", width : "10%"},
            {title : "操作", width : "10%", formatter : function(value, row, index) {
                    var _html = '';
                    if (hasPermission('quartz:job:log:edit')) {
                        _html += '<a href="javascript:;" onclick="vm.edit(\''+row.logId+'\')" title="编辑"><i class="fa fa-pencil"></i></a>';
                    }
                    if (hasPermission('quartz:job:log:remove')) {
                        _html += '<a href="javascript:;" onclick="vm.remove(false,\''+row.logId+'\')" title="删除"><i class="fa fa-trash-o"></i></a>';
                    }
                    return _html;
                }
            }
		]
	})
}

var vm = new Vue({
	el:'#dpLTE',
	data: {
        jobId:null,
        beanName:null,
        methodName:null,
        params:null,
        status:null,
        error:null,
        times:null,
        gmtCreate:null,
	},
	methods : {
		load: function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		save: function() {
			dialogOpen({
				title: '新增定时任务日志; InnoDB free: 27648 kB',
				url: 'base/quartz/job/log/add.html?_' + $.now(),
				width: '420px',
				height: '350px',
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
		edit: function(logId) {
            dialogOpen({
                title: '编辑定时任务日志; InnoDB free: 27648 kB',
                url: 'base/quartz/job/log/edit.html?_' + $.now(),
                width: '420px',
                height: '350px',
                success: function(iframeId){
                    top.frames[iframeId].vm.quartzJobLog.logId = logId;
                    top.frames[iframeId].vm.setForm();
                },
                yes: function(iframeId){
                    top.frames[iframeId].vm.acceptClick();
                }
            });
        },
        remove: function(batch, logId) {
            var ids = [];
            if (batch) {
                var ck = $('#dataGrid').bootstrapTable('getSelections');
                if (!checkedArray(ck)) {
                    return false;
                }
                $.each(ck, function(idx, item){
                    ids[idx] = item.logId;
                });
            } else {
                ids.push(logId);
            }
            $.RemoveForm({
                url: '../../quartz/job/log/remove?_' + $.now(),
                param: ids,
                success: function(data) {
                    vm.load();
                }
            });
        },
        clear: function() {
            $.ConfirmAjax({
                msg : "您确定要清空日志吗？",
                url: '../../quartz/job/log/clear?_' + $.now(),
                success: function(data) {
                    vm.load();
                }
            });
        }
	}
})