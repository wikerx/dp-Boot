/**
 * 新增-InnoDB free: 9216 kBjs
 */
$(function(){
	vm.getUserList();
})
var vm = new Vue({
	el:'#dpLTE',
	data: {
		sysDepartment: {
			id: 0,
			userId:0,
			username:null,
			name:null,
			creater:null,
			createTime:null,
			discribe:null,
			manager:null,
			managerId:null,
			check:null,
			userIdList:[]
		}
	},
	methods : {
		getUserList: function(){
			$('.userSelect').selectBindEx({
				url: '../../sys/user/selectAll?_' + $.now(),
				placeholder: '请选择部门经理',
				value: 'userId',
				text: 'username'
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
			vm.sysDepartment.userIdList = $('.userSelect').val();
			vm.sysDepartment.managerId = $('.userSelect').val();
		    $.SaveForm({
		    	url: '../../sys/dept/save?_' + $.now(),
		    	param: vm.sysDepartment,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
