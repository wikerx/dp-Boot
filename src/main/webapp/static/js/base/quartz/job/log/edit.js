/**
 * 编辑-定时任务日志; InnoDB free: 27648 kBjs
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		quartzJobLog: {
			logId: 0
		}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: '../../quartz/job/log/info?_' + $.now(),
		    	param: vm.quartzJobLog.logId,
		    	success: function(data) {
		    		vm.quartzJobLog = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: '../../quartz/job/log/update?_' + $.now(),
		    	param: vm.quartzJobLog,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})