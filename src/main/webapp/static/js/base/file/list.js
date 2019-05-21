/**
 * InnoDB free: 23552 kBjs
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
		url: '../../sys/file/list?_' + $.now(),
		height: $(window).height()-56,
		queryParams: function(params){
                  params.fileName = vm.fileName;
            console.log(vm.fileName)
			return params;
		},
		columns: [
			{checkbox: true},
            {field : "fileName", title : "文件名称", width : "100px"},
            {field : "fileAdd", title : "文件位置", width : "100px"},
            /*{field : "fileUpId", title : "上传者编号", width : "100px"},*/
            {field : "remark", title : "上传说明", width : "100px"},
            {field : "upTime", title : "上传时间", width : "100px"},
            {field : "fileType", title : "文件类型 ", width : "100px",formatter : function(value, row, index) {return "文档"}},
            {field : "fileNewName", title : "新文件名称", width : "100px"},
            {title : "操作", formatter : function(value, row, index) {
                    var _html = '';
                    if (hasPermission('sys:file:edit')) {
                        _html += '<a href="javascript:;" onclick="vm.edit(\''+row.fileId+'\')" title="编辑"><i class="fa fa-pencil"></i></a>';
                    }
                    if (hasPermission('sys:file:remove')) {
                        _html += '<a href="javascript:;" onclick="vm.remove(false,\''+row.fileId+'\')" title="删除"><i class="fa fa-trash-o"></i></a>';
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
        fileName:null
	},
	methods : {
		load: function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		save: function() {
			dialogOpen({
				title: '新增InnoDB free: 23552 kB',
				url: 'base/file/add.html?_' + $.now(),
				width: '420px',
				height: '350px',
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
		edit: function(fileId) {
            dialogOpen({
                title: '编辑InnoDB free: 23552 kB',
                url: 'base/file/edit.html?_' + $.now(),
                width: '420px',
                height: '350px',
                success: function(iframeId){
                    top.frames[iframeId].vm.sysFile.fileId = fileId;
                    top.frames[iframeId].vm.setForm();
                },
                yes: function(iframeId){
                    top.frames[iframeId].vm.acceptClick();
                }
            });
        },
        remove: function(batch, fileId) {
            var ids = [];
            if (batch) {
                var ck = $('#dataGrid').bootstrapTable('getSelections');
                if (!checkedArray(ck)) {
                    return false;
                }
                $.each(ck, function(idx, item){
                    ids[idx] = item.fileId;
                });
            } else {
                ids.push(fileId);
            }
            $.RemoveForm({
                url: '../../sys/file/remove?_' + $.now(),
                param: ids,
                success: function(data) {
                    vm.load();
                }
            });
        }
	}
})