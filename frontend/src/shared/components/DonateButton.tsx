import { ComponentProps, ReactNode } from "react";

type DonateButtonProps = ComponentProps<"a"> & {
  children: ReactNode;
};

export const DonateButton = ({ children, ...props }: DonateButtonProps) => {
  return (
    <a {...props} target="_blank" rel="noopener noreferrer">
      {children}
    </a>
  );
};
