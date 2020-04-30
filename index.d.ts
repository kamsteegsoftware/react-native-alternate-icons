declare module 'react-native-alternate-icons' {
  export const reset: () => void;
  export const supportDevice: (callback: (supported: boolean) => any) => void;
  export const getIconName: (callback: (currentName: string) => any) => void;
  export const setIconName: (name: string | null) => void;
}
