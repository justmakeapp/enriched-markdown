import type { ComponentType } from 'react';
import type { StyleProp, ViewStyle } from 'react-native';

import type { MarkdownStyle as MarkdownStyleDefinition } from './types/MarkdownStyle';

export type MarkdownStyle = MarkdownStyleDefinition;

export interface EnrichedMarkdownTextProps {
  markdown: string;
  flavor?: 'commonmark' | 'github';
  md4cFlags?: Readonly<Record<string, boolean>>;
  markdownStyle?: MarkdownStyle;
  containerStyle?: StyleProp<ViewStyle>;
  onLinkPress?: (event: { url: string }) => void;
  enableTaskListItemToggle?: boolean;
}

export const EnrichedMarkdownText: ComponentType<EnrichedMarkdownTextProps>;
