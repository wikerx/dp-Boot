/**
 * 文章js
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
		url: '../../doc/arc/list?_' + $.now(),
		height: $(window).height()-56,
		queryParams: function(params){
                  params.id = vm.id;
                  params.title = vm.title;
                  params.createTime = vm.createTime;
                  params.updateTime = vm.updateTime;
                  params.creator = vm.creator;
                  params.category = vm.category;
                  params.clicks = vm.clicks;
                  params.status = vm.status;
                  params.tag = vm.tag;
                  params.type = vm.type;
                  params.attribute = vm.attribute;
                  params.content = vm.content;
			return params;
		},
		columns: [
			{checkbox: true},
            {field : "title", title : "文章标题", align:"center",width : "9%"},
            {field : "createTime", title : "发布时间", align:"center",width : "9%"},
            {field : "updateTime", title : "修改时间", align:"center",width : "9%"},
            {field : "creator", title : "发布人", align:"center",width : "9%"},
            {field : "category", title : "类目", align:"center",width : "9%"},
            {field : "clicks", title : "浏览量", align:"center",width : "9%"},
            {field : "status", title : "状态", align:"center",width : "9%",formatter : function(value, row, index) {
                    if(value ==0){
                        return "<span class='label label-default'>待审核</span>";
                    }else if(value == 1){
                        return "<span class='label label-success'>开放浏览</span>";
                    }else if(value == 2){
                        return "<span class='label label-warning'>审核不通过</span>";
                    }else if(value == 3){
                        return "<span class='label label-default'>文章下架</span>";
                    }else if(value == 4){
                        return "<span class='label label-info'>只读</span>";
                    }else{
                        return "<span class='label label-danger'>未知状态</span>";
                    }
                }},
            {field : "tag", title : "标签属性", align:"center",width : "9%",formatter : function(value, row, index) {
                var arr = value.split(";");
                    var _html = "";
                    arr.forEach(function(v){
                        _html += "<span class='label label-info'>"+v+"</span>&nbsp;";
                    });
                return _html;
                }},
            {field : "type", title : "所属分类", align:"center",width : "9%"},
            {field : "attribute", title : "综合属性", align:"center",width : "9%",formatter : function(value, row, index) {
                    if(value ==0){
                        return "<span class='label label-default'>普通</span>";
                    }else if(value == 1){
                        return "<span class='label label-info'>小人气</span>";
                    }else if(value == 2){
                        return "<span class='label label-warning'>人气榜</span>";
                    }else if(value == 3){
                        return "<span class='label label-danger'>热门聚焦</span>";
                    }else{
                        return "<span class='label label-default'>未知状态</span>";
                    }
                }},
            /*{field : "content", title : "内容", align:"center",width : "100px"},*/
            {title : "操作",align:"center", formatter : function(value, row, index) {
                    var _html = '';
                    if (hasPermission('doc:arc:edit')) {
                        _html += '<a href="javascript:;" onclick="vm.edit(\''+row.id+'\')" title="编辑"><i class="fa fa-pencil"></i></a>';
                    }
                    if (hasPermission('doc:arc:list')) {
                        _html += '<a href="javascript:;" onclick="vm.edit(\''+row.id+'\')" title="查看详情"><i class="fa fa-eye-slash"></i></a>';
                    }
                    if (hasPermission('doc:arc:remove')) {
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
        createTime:null,
        updateTime:null,
        creator:null,
        category:null,
        clicks:null,
        status:null,
        tag:null,
        type:null,
        attribute:null,
        content:null,
        person:null,
	},
	methods : {
		load: function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		save: function() {
			dialogOpen({
				title: '新增文章',
				url: 'doc/arc/add.html?_' + $.now(),
                scroll : true,
				width: '900px',
				height: '650px',
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
		edit: function(id) {
            dialogOpen({
                title: '编辑文章',
                url: 'doc/arc/edit.html?_' + $.now(),
                scroll : true,
                width: '900px',
                height: '650px',
                success: function(iframeId){
                    top.frames[iframeId].vm.docArticle.id = id;
                    top.frames[iframeId].vm.setForm();
                },
                yes: function(iframeId){
                    top.frames[iframeId].vm.acceptClick();
                }
            });
        },
        see: function(id) {
            dialogOpen({
                title: '查看文章',
                url: 'doc/arc/see.html?_' + $.now(),
                scroll : true,
                width: '900px',
                height: '650px',
                success: function(iframeId){
                    top.frames[iframeId].vm.docArticle.id = id;
                    top.frames[iframeId].vm.setForm();
                },
                // yes: function(iframeId){
                //     top.frames[iframeId].vm.acceptClick();
                // }
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
                url: '../../doc/arc/remove?_' + $.now(),
                param: ids,
                success: function(data) {
                    vm.load();
                }
            });
        }
	}
})