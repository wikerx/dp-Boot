/**
 * InnoDB free: 9216 kBjs
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
		url: '../../sys/dept/list?_' + $.now(),
		height: $(window).height()-56,
		queryParams: function(params){
			params.name = vm.name;
			params.manager = vm.manager;
			return params;
		},
		columns: [
			{checkbox: true},
            {field : "name", title : "部门名称", width : "15%"},
            {field : "creater", title : "创建人", width : "15%"},
            {field : "createTime", title : "创建时间", width : "15%"},
            {field : "discribe", title : "部门描述信息", width : "15%"},
            {field : "manager", title : "部门经理", width : "15%"},
            {field : "check", title : "信息查看权限", width : "15%",formatter : function(value , row, index) {
                if(value === 0){
                    if (hasPermission('sys:dept:list')) {
                        return '<b style="color: #FD482C;"><i class="fa fa-warning"></i>信息共享</b>';
                    } else {
                        return '<i class="fa fa-toggle-on"></i>';
                    }
                }
                if(value === 1){
                    if (hasPermission('sys:dept:list')) {
                        return '<b style="color: #00B83F;font-size: 12px;">经理看所有，其他人只能看自己</b>';
                    } else {
                        return '<i class="fa fa-toggle-on"></i>';
                    }
                }
            }
            },
            {title : "操作", formatter : function(value, row, index) {
                    var _html = '';
                    if (hasPermission('sys:dept:edit')) {
                        _html += '<a href="javascript:;" onclick="vm.edit(\''+row.id+'\')" title="编辑"><i class="fa fa-pencil"></i></a>';
                    }
                    if (hasPermission('sys:dept:remove')) {
                        _html += '<a href="javascript:;" onclick="vm.remove(false,\''+row.id+'\')" title="删除"><i class="fa fa-trash-o"></i></a>';
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
        name:null,
        creater:null,
        createTime:null,
        discribe:null,
        manager:null,
        check:null,
        id:null
	},
	methods : {
		load: function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		save: function() {
			dialogOpen({
				title: '部门信息',
				url: 'base/dept/add.html?_' + $.now(),
				width: '420px',
				height: '450px',
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
		edit: function(id) {
            dialogOpen({
                title: '部门信息',
                url: 'base/dept/edit.html?_' + $.now(),
                width: '420px',
                height: '550px',
                success: function(iframeId){
                    top.frames[iframeId].vm.sysDepartment.id = id;
                    top.frames[iframeId].vm.setForm();
                },
                yes: function(iframeId){
                    top.frames[iframeId].vm.acceptClick();
                }
            });
        },
        remove: function(batch, id) {
            var ids = [];
            if (batch) {
                var ck = $('#dataGrid').bootstrapTable('getSelections');
                if (!checkedArray(ck)) {
                    return false;
                }
                $.each(ck, function(idx, item){
                    ids[idx] = item.id;
                });
            } else {
                ids.push(id);
            }
            $.RemoveForm({
                url: '../../sys/dept/remove?_' + $.now(),
                param: ids,
                success: function(data) {
                    vm.load();
                }
            });
        }
	}
})