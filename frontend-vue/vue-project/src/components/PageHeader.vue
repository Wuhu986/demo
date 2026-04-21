<template>
  <div class="header">
    <div class="logo" @click="$router.push('/products')">
      <el-icon class="logo-icon" :size="22"><Shop /></el-icon>
      <span class="logo-text">Used Market</span>
    </div>
    <div class="right">
      <language-switcher />
      <el-button text @click="$router.push('/orders')">{{ $t('header.orders') }}</el-button>
      <el-button type="primary" size="small" @click="$router.push('/publish')">
        + {{ $t('header.publish') }}
      </el-button>

      <!-- 余额 chip -->
      <div class="balance-chip" @click="showRecharge = true">
        <el-icon :size="14"><Wallet /></el-icon>
        <span>¥ {{ balanceDisplay }}</span>
        <span class="recharge-hint">{{ $t('balance.recharge') }}</span>
      </div>

      <div v-if="nickname" class="user-chip">
        <span class="user-avatar">{{ nickname[0] }}</span>
        <span class="user-name">{{ nickname }}</span>
      </div>
      <el-button text size="small" class="logout-btn" @click="logout">
        {{ $t('header.logout') }}
      </el-button>
    </div>
  </div>

  <!-- 充值弹窗 -->
  <el-dialog v-model="showRecharge" :title="$t('balance.rechargeTitle')" width="400px" align-center>
    <div class="recharge-body">
      <p class="current-balance">
        {{ $t('balance.title') }}：<b>¥ {{ balanceDisplay }}</b>
      </p>
      <p class="preset-label">{{ $t('balance.presets') }}</p>
      <div class="presets">
        <el-button
          v-for="p in presets"
          :key="p"
          :type="rechargeAmount === p ? 'primary' : 'default'"
          plain
          @click="rechargeAmount = p"
        >¥ {{ p }}</el-button>
      </div>
      <el-input-number
        v-model="rechargeAmount"
        :min="1"
        :max="99999"
        :precision="2"
        :placeholder="$t('balance.amountPlaceholder')"
        style="width:100%;margin-top:16px"
      />
    </div>
    <template #footer>
      <el-button @click="showRecharge = false">取消</el-button>
      <el-button type="primary" :loading="recharging" @click="doRecharge">
        {{ $t('balance.confirm') }}
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Shop, Wallet } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import LanguageSwitcher from './LanguageSwitcher.vue'
import { getBalance, recharge } from '@/api/user'

const router = useRouter()
const nickname = ref('')
const balance = ref(0)
const showRecharge = ref(false)
const rechargeAmount = ref(100)
const recharging = ref(false)
const presets = [100, 500, 1000, 3000]

const balanceDisplay = computed(() => Number(balance.value || 0).toFixed(2))

const fetchBalance = async () => {
  try {
    const res = await getBalance()
    if (res.data.code === 200) balance.value = res.data.data ?? 0
  } catch (e) { /* ignore */ }
}

onMounted(() => {
  nickname.value = localStorage.getItem('nickname') || ''
  fetchBalance()
})

const doRecharge = async () => {
  if (!rechargeAmount.value || rechargeAmount.value <= 0) {
    ElMessage.warning('请输入有效的充值金额')
    return
  }
  recharging.value = true
  try {
    const res = await recharge(rechargeAmount.value)
    if (res.data.code === 200) {
      balance.value = res.data.data
      ElMessage.success(`充值成功，当前余额 ¥${Number(res.data.data).toFixed(2)}`)
      showRecharge.value = false
    } else {
      ElMessage.error(res.data.msg || '充值失败')
    }
  } finally {
    recharging.value = false
  }
}

const logout = () => {
  localStorage.clear()
  router.push('/login')
}

// 让 Pay.vue 可以触发余额刷新
defineExpose({ fetchBalance })
</script>

<style scoped>
.header {
  height: 60px;
  background: white;
  border-bottom: 1px solid #ebebeb;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 28px;
  position: sticky;
  top: 0;
  z-index: 100;
  box-shadow: 0 1px 6px rgba(0,0,0,0.06);
}
.logo {
  display: flex;
  align-items: center;
  gap: 7px;
  cursor: pointer;
  user-select: none;
}
.logo-icon { color: #667eea; }
.logo-text {
  font-weight: 700;
  font-size: 18px;
  color: #667eea;
}
.right {
  display: flex;
  align-items: center;
  gap: 10px;
}
.balance-chip {
  display: flex;
  align-items: center;
  gap: 5px;
  background: #fef3c7;
  color: #d97706;
  border-radius: 999px;
  padding: 5px 12px;
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s;
  user-select: none;
}
.balance-chip:hover { background: #fde68a; }
.recharge-hint {
  font-size: 11px;
  color: #92400e;
  border-left: 1px solid #fcd34d;
  padding-left: 6px;
  margin-left: 2px;
}
.user-chip {
  display: flex;
  align-items: center;
  gap: 7px;
  background: #f3f4f6;
  border-radius: 999px;
  padding: 4px 12px 4px 6px;
}
.user-avatar {
  width: 26px;
  height: 26px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  font-size: 12px;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
}
.user-name {
  font-size: 13px;
  color: #374151;
  font-weight: 500;
  max-width: 100px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.logout-btn { color: #9ca3af !important; }
.logout-btn:hover { color: #f56c6c !important; }

/* 弹窗内容 */
.recharge-body { padding: 4px 0 8px; }
.current-balance {
  font-size: 15px;
  color: #374151;
  margin-bottom: 16px;
}
.current-balance b { color: #d97706; font-size: 18px; }
.preset-label { font-size: 13px; color: #9ca3af; margin-bottom: 8px; }
.presets { display: flex; gap: 8px; flex-wrap: wrap; }
</style>