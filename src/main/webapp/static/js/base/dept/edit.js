/**
 * 编辑-InnoDB free: 9216 kBjs
 */
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
		setForm: function() {
			$.SetForm({
				url: '../../sys/dept/info?_' + $.now(),
		    	param: vm.sysDepartment.id,
		    	success: function(data) {
		    		vm.sysDepartment = data;
					vm.getUserList();
		    	}
			});
		},
		getUserList: function(){
			$('.userSelect').selectBindEx({
				url: '../../sys/user/select?deptId='+vm.sysDepartment.id+'&_' + $.now(),
				placeholder: '请选择部门经理',
				value: 'userId',
				text: 'username',
				selected: vm.sysDepartment.userIdList
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
			vm.sysDepartment.userIdList = $('.userSelect').val();
			vm.sysDepartment.managerId = $('.userSelect').val();
		    $.ConfirmForm({
		    	url: '../../sys/dept/update?_' + $.now(),
		    	param: vm.sysDepartment,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})