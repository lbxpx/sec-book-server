// Utilities
import { PiniaPluginContext, defineStore } from "pinia";

const defaultNaviList: NaviItem[] = [
  { name: "Home", children: [] },
  { name: "Main", children: ["Start", "Detail"] },
  { name: "Login", children: [] },
];

export const useAppStore = defineStore("app", {
  state: () => ({
    userInfo: {
      loggedIn: false,
      username: "xyz",
      jwtToken: "abc",
    } as UserInfo,
    systemConfig: {
      naviList: defaultNaviList,
    } as SystemConfig,
  }),
  actions: {
    async login(username: string, password: string) {
      try {
        const jwtToken = "abc";
        this.userInfo = {
          loggedIn: true,
          username: username,
          jwtToken: jwtToken,
        };
        console.log(this.userInfo);
      } catch (error: any) {
        console.log(error);
      }
    },
    logout() {
      this.userInfo = {
        loggedIn: false,
        username: "",
        jwtToken: "",
      };
    },
    setNavi(naviList: NaviItem[]) {
      this.systemConfig.naviList = naviList;
    },
  },
  persist: {
    storage: localStorage,
    // debug: true,
    // afterRestore: (context: PiniaPluginContext) => {
    //   console.log(context);
    // },
  },
});

interface UserInfo {
  loggedIn: boolean;
  username: string;
  jwtToken: string;
}

interface NaviItem {
  name: string;
  children: string[];
}

interface SystemConfig {
  naviList: NaviItem[];
}
