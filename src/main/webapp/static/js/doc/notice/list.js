/**
 * 公告js
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
		url: '../../doc/notice/list?_' + $.now(),
		height: $(window).height()-56,
		queryParams: function(params){
                  params.id = vm.id;
                  params.title = vm.title;
                  params.creator = vm.creator;
                  params.createTime = vm.createTime;
                  params.endTime = vm.endTime;
                  params.status = vm.status;
                  params.intendedFor = vm.intendedFor;
                  params.content = vm.content;
                  params.person = vm.person;
			return params;
		},
		columns: [
			{checkbox: true},
            {field : "title", title : "标题", align:"center",width : "15%"},
            {field : "person", title : "发布者", align:"center",width : "15%"},
            {field : "createTime", title : "发布时间", align:"center",width : "15%"},
            {field : "endTime", title : "截止时间", align:"center",width : "15%"},
            {field : "status", title : "状态", align:"center",width : "15%",formatter : function(value, row, index) {
                    if(value ==0){
                        return "<span class='label label-default'>过期</span>";
                    }else if(value == 1){
                        return "<span class='label label-success'>生效</span>";
                    }else{
                        return "<span class='label label-danger'>未知状态</span>";
                    }
                }},
            {field : "intendedFor", title : "适用人群", align:"center",width : "15%",formatter : function(value, row, index) {
                if(value =="N"){
                    return "<span class='label label-info'>互联网用户</span>";
                }else if(value == "Y"){
                    return "<span class='label label-success'>内部员工</span>";
                }else{
                    return "<span class='label label-danger'>未知用户</span>";
                }
                }},
            /*{field : "content", title : "", align:"center",width : "15%"},*/
            {title : "操作", align:"center",formatter : function(value, row, index) {
                    var _html = '';
                    if (hasPermission('doc:notice:edit')) {
                        _html += '<a href="javascript:;" onclick="vm.edit(\''+row.id+'\')" title="编辑"><i class="fa fa-pencil"></i></a>';
                    }
                    if (hasPermission('doc:notice:list')) {
                        _html += '<a href="javascript:;" onclick="vm.edit(\''+row.id+'\')" title="查看详情"><i class="fa fa-eye-slash"></i></a>';
                    }
                    if (hasPermission('doc:notice:remove')) {
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
        title:null,
        creator:null,
        createTime:null,
        endTime:null,
        status:null,
        intendedFor:null,
        content:null,
        person:null,

	},
	methods : {
		load: function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		save: function() {
			dialogOpen({
				title: '新增公告',
				url: 'doc/notice/add.html?_' + $.now(),
                scroll : true,
				width: '420px',
				height: '350px',
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
		edit: function(id) {
            dialogOpen({
                title: '编辑公告',
                url: 'doc/notice/edit.html?_' + $.now(),
                scroll : true,
                width: '420px',
                height: '350px',
                success: function(iframeId){
                    top.frames[iframeId].vm.docNotice.id = id;
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
                url: '../../doc/notice/remove?_' + $.now(),
                param: ids,
                success: function(data) {
                    vm.load();
                }
            });
        }
	}
})