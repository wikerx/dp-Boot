/**
 * 新增-定时任务日志; InnoDB free: 27648 kBjs
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		quartzJobLog: {
			logId: 0
		}
	},
	methods : {
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: '../../quartz/job/log/save?_' + $.now(),
		    	param: vm.quartzJobLog,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
