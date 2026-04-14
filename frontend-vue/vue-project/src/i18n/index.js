import { createI18n } from 'vue-i18n'
import zh from './zh'
import ja from './ja'

const locale = localStorage.getItem('lang') || 'zh'

const i18n = createI18n({
    legacy: false,
    globalInjection: true,
    locale,
    messages: {
        zh,
        ja,
    },
})

export default i18n